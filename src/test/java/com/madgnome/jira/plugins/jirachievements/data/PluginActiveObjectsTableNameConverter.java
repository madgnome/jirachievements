//package com.madgnome.jira.plugins.jirachievements.data;
//
//import com.atlassian.activeobjects.ao.ActiveObjectsTableNameConverter;
//import com.atlassian.activeobjects.internal.Prefix;
//import com.madgnome.jira.plugins.jirachievements.utils.data.AOUtil;
//import net.java.ao.RawEntity;
//import net.java.ao.schema.TableNameConverter;
//
//public final class PluginActiveObjectsTableNameConverter implements TableNameConverter
//{
//
//    private final TableNameConverter tnc = new ActiveObjectsTableNameConverter(new Prefix()
//    {
//        @Override
//        public String prepend(String string)
//        {
//            return AOUtil.TABLE_PREFIX + "_" + string;
//        }
//
//        @Override
//        public boolean isStarting(String string, boolean caseSensitive)
//        {
//            final String thePrefix = caseSensitive ? AOUtil.TABLE_PREFIX : AOUtil.TABLE_PREFIX.toLowerCase();
//            final String toCompare = caseSensitive ? string : string.toLowerCase();
//            return toCompare.startsWith(thePrefix);
//        }
//    });
//
//    @Override
//    public String getName(Class<? extends RawEntity<?>> clazz)
//    {
//        return tnc.getName(clazz);
//    }
//}