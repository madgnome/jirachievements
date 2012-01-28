package com.madgnome.jira.plugins.jirachievements.stream;

import com.atlassian.plugin.webresource.UrlMode;
import com.atlassian.plugin.webresource.WebResourceUrlProvider;
import com.atlassian.sal.api.message.I18nResolver;
import com.atlassian.streams.api.*;
import com.atlassian.streams.api.common.ImmutableNonEmptyList;
import com.atlassian.streams.api.common.Option;
import com.atlassian.streams.spi.CancellableTask;
import com.atlassian.streams.spi.CancelledException;
import com.atlassian.streams.spi.StreamsActivityProvider;
import com.atlassian.streams.spi.UserProfileAccessor;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.madgnome.jira.plugins.jirachievements.data.ao.Level;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserAchievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserLevel;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserLevelDaoService;
import org.joda.time.DateTime;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ActivityProvider implements StreamsActivityProvider
{
  private final IUserAchievementDaoService userAchievementDaoService;
  private final IUserLevelDaoService userLevelDaoService;

  private final WebResourceUrlProvider webResourceUrlProvider;
  private final UserProfileAccessor userProfileAccessor;
  private final I18nResolver i18nResolver;

  public ActivityProvider(IUserAchievementDaoService userAchievementDaoService, UserProfileAccessor userProfileAccessor, I18nResolver i18nResolver, WebResourceUrlProvider webResourceUrlProvider, IUserLevelDaoService userLevelDaoService)
  {
    this.userAchievementDaoService = userAchievementDaoService;
    this.userProfileAccessor = userProfileAccessor;
    this.i18nResolver = i18nResolver;
    this.webResourceUrlProvider = webResourceUrlProvider;
    this.userLevelDaoService = userLevelDaoService;
  }

  @Override
  public CancellableTask<StreamsFeed> getActivityFeed(final ActivityRequest activityRequest) throws StreamsException
  {
    return new CancellableTask<StreamsFeed>()
    {
      final AtomicBoolean cancelled = new AtomicBoolean(false);

      @Override
      public StreamsFeed call() throws Exception
      {
        final int maxResults = activityRequest.getMaxResults();
        List<UserAchievement> userAchievements = userAchievementDaoService.last(maxResults);
        List<UserLevel> userLevels = userLevelDaoService.last(maxResults);

        if (cancelled.get())
        {
          throw new CancelledException();
        }

        List<StreamsEntry> achievementLogEntries = transformAchievementEntries(userAchievements);
        List<StreamsEntry> levelsLogEntries = transformLevelEntries(userLevels);

        List<StreamsEntry> entries = new ArrayList<StreamsEntry>(achievementLogEntries);
        entries.addAll(levelsLogEntries);
        
        return new StreamsFeed("", entries, Option.<String>none());
      }

      @Override
      public Result cancel()
      {
        cancelled.set(true);
        return Result.CANCELLED;
      }
    };
  }

  private List<StreamsEntry> transformAchievementEntries(List<UserAchievement> userAchievements)
  {
    List<StreamsEntry> entries = Lists.transform(userAchievements,
            new Function<UserAchievement, StreamsEntry>()
            {
              public StreamsEntry apply(UserAchievement from)
              {
                return achievementToStreamsEntry(from);
              }
            });

    return entries;
  }

  private List<StreamsEntry> transformLevelEntries(List<UserLevel> userLevels)
  {
    List<StreamsEntry> entries = Lists.transform(userLevels,
            new Function<UserLevel, StreamsEntry>()
            {
              public StreamsEntry apply(UserLevel from)
              {
                return levelToStreamsEntry(from);
              }
            });

    return entries;
  }

  private StreamsEntry levelToStreamsEntry(final UserLevel userLevel)
  {
    final UserProfile userProfile = userProfileAccessor.getUserProfile(userLevel.getUserWrapper().getJiraUserName());

    final URI userAchievementURI = URI.create(userProfile.getProfilePageUri().get() + "#selectedTab=com.madgnome.jira.plugins.jirachievements:achievements-profile");

    StreamsEntry.ActivityObject activityObject = new StreamsEntry.ActivityObject(StreamsEntry.ActivityObject.params()
            .id("").alternateLinkUri(URI.create(""))
            .activityObjectType(Option.<ActivityObjectType>none()));


    StreamsEntry.Renderer renderer = new StreamsEntry.Renderer()
    {
      public Html renderTitleAsHtml(StreamsEntry entry)
      {
        String userHtml = (userProfile.getProfilePageUri().isDefined()) ?
                String.format("<a href=\"%s\" class=\"activity-item-user activity-item-author\">%s</a>",
                        userAchievementURI.toString(), userProfile.getUsername()) :
                userProfile.getUsername();
        final Level level = userLevel.getLevel();
        String levelHtml = String.format("<a href=\"%s\">%s</a>", "", level.getCategory().toString());

        return new Html(userHtml + " levels up as a " + levelHtml + " to level " + level.getLevelNumber());
      }

      public Option<Html> renderSummaryAsHtml(StreamsEntry entry)
      {
        return Option.none();
      }

      public Option<Html> renderContentAsHtml(StreamsEntry entry)
      {
        return Option.none();
      }
    };

    ActivityVerb verb =  ActivityVerbs.like();

    StreamsEntry streamsEntry = new StreamsEntry(StreamsEntry.params()
            .id(userAchievementURI)
            .postedDate(new DateTime(userLevel.getCreatedOn()))
            .authors(ImmutableNonEmptyList.of(userProfile))
            .addActivityObject(activityObject)
            .verb(verb)
            .addLink(URI.create(webResourceUrlProvider.getStaticPluginResourceUrl(
                    "com.madgnome.jira.plugins.jirachievements:jh-stream-webresource",
                    "jh-icon.png",
                    UrlMode.ABSOLUTE)),
                    StreamsActivityProvider.ICON_LINK_REL,
                    Option.<String>none())
            .alternateLinkUri(userAchievementURI)
            .renderer(renderer)
            .applicationType("JIRA Hero"), i18nResolver);

    return streamsEntry;
  }

  private StreamsEntry achievementToStreamsEntry(final UserAchievement userAchievement)
  {
    final UserProfile userProfile = userProfileAccessor.getUserProfile(userAchievement.getUserWrapper().getJiraUserName());

    final URI userAchievementURI = URI.create(userProfile.getProfilePageUri().get() + "#selectedTab=com.madgnome.jira.plugins.jirachievements:achievements-profile");

    StreamsEntry.ActivityObject activityObject = new StreamsEntry.ActivityObject(StreamsEntry.ActivityObject.params()
            .id("").alternateLinkUri(URI.create(""))
            .activityObjectType(Option.<ActivityObjectType>none()));


    StreamsEntry.Renderer renderer = new StreamsEntry.Renderer()
    {
      public Html renderTitleAsHtml(StreamsEntry entry)
      {
        String userHtml = (userProfile.getProfilePageUri().isDefined()) ? 
                            String.format("<a href=\"%s\" class=\"activity-item-user activity-item-author\">%s</a>",
                                          userAchievementURI.toString(), userProfile.getUsername()) :
                            userProfile.getUsername();
        String achievementHtml = String.format("<a href=\"%s\">%s</a>", "", userAchievement.getAchievement().getName());

        return new Html(userHtml + " earned the " + achievementHtml + " achievement!");
      }

      public Option<Html> renderSummaryAsHtml(StreamsEntry entry)
      {
        return Option.option(new Html(userAchievement.getAchievement().getDescription()));
      }

      public Option<Html> renderContentAsHtml(StreamsEntry entry)
      {
        return Option.none();
      }
    };

    ActivityVerb verb =  ActivityVerbs.like();

    StreamsEntry streamsEntry = new StreamsEntry(StreamsEntry.params()
            .id(userAchievementURI)
            .postedDate(new DateTime(userAchievement.getCreatedOn()))
            .authors(ImmutableNonEmptyList.of(userProfile))
            .addActivityObject(activityObject)
            .verb(verb)
            .addLink(URI.create(webResourceUrlProvider.getStaticPluginResourceUrl(
                    "com.madgnome.jira.plugins.jirachievements:jh-stream-webresource",
                    "jh-icon.png",
                    UrlMode.ABSOLUTE)),
                    StreamsActivityProvider.ICON_LINK_REL,
                    Option.<String>none())
            .alternateLinkUri(userAchievementURI)
            .renderer(renderer)
            .applicationType("JIRA Hero"), i18nResolver);

    return streamsEntry;
  }
}
