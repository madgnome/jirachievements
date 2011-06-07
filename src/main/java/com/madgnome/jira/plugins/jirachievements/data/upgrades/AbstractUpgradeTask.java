package com.madgnome.jira.plugins.jirachievements.data.upgrades;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.external.ActiveObjectsUpgradeTask;
import com.atlassian.activeobjects.external.ModelVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUpgradeTask implements ActiveObjectsUpgradeTask
{
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public final ModelVersion getModelVersion()
  {
    return ModelVersion.valueOf(String.valueOf(getVersion()));
  }

  @Override
  public final void upgrade(ModelVersion currentVersion, ActiveObjects ao)
  {
    logger.debug("Running upgrade task for model #{}, ao {}", getModelVersion(), ao);
    doUpgrade(ao);
  }

  protected abstract int getVersion();

  protected abstract void doUpgrade(ActiveObjects ao);
}