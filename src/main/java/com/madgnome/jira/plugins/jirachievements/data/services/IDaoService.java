package com.madgnome.jira.plugins.jirachievements.data.services;

import net.java.ao.Entity;

import java.util.List;

public interface IDaoService<T extends Entity>
{
  T get(int id);
  List<T> all();
}
