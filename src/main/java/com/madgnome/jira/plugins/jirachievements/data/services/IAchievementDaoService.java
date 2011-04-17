package com.madgnome.jira.plugins.jirachievements.data.services;

import com.atlassian.activeobjects.tx.Transactional;
import com.madgnome.jira.plugins.jirachievements.data.ao.Achievement;

@Transactional
public interface IAchievementDaoService extends IReferencableDaoService<Achievement>
{

}
