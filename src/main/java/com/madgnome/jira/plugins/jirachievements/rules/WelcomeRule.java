package com.madgnome.jira.plugins.jirachievements.rules;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.util.UserUtil;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;
import com.madgnome.jira.plugins.jirachievements.data.ao.AchievementRefEnum;
import com.madgnome.jira.plugins.jirachievements.data.ao.UserWrapper;
import com.madgnome.jira.plugins.jirachievements.data.services.IAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserAchievementDaoService;
import com.madgnome.jira.plugins.jirachievements.data.services.IUserWrapperDaoService;

public class WelcomeRule extends AbstractRule implements IRule
{
  private final UserUtil userUtil;

  public WelcomeRule(JiraAuthenticationContext jiraAuthenticationContext, IUserWrapperDaoService userWrapperDaoService, IAchievementDaoService achievementDaoService, IUserAchievementDaoService userAchievementDaoService, UserUtil userUtil)
  {
    super(jiraAuthenticationContext, userWrapperDaoService, achievementDaoService, userAchievementDaoService);
    this.userUtil = userUtil;
  }

  @Override
  public AchievementRefEnum getAchievementRef()
  {
    return AchievementRefEnum.WELCOME;
  }

  @Override
  public void check()
  {
    for (User user : userUtil.getUsers())
    {
      if (userWrapperDaoService.get(user) == null)
      {
        userWrapperDaoService.create(user);

        execute(user);
      }
    }
  }

  private void execute(User user)
  {
    UserWrapper userWrapper = userWrapperDaoService.get(user);

    Achievement achievement = achievementDaoService.get(getAchievementRef());
    if (userAchievementDaoService.get(achievement, userWrapper) == null)
    {
      userAchievementDaoService.addAchievementToUser(achievement, userWrapper);
    }
  }


}
