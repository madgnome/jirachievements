package com.madgnome.jira.plugins.jirachievements.data.services;

/**
 * @author <a href="mailto:julien.hoarau@kalistick.fr">Julien HOARAU</a>
 * @version $Id$
 */
public interface Action<T>
{
  void execute(T param);
}
