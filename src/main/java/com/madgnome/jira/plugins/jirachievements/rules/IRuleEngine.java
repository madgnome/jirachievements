package com.madgnome.jira.plugins.jirachievements.rules;

public interface IRuleEngine<T>
{
  void execute(T input);
}
