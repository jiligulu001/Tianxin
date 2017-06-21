package com.tianxin.tianxin.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by zhy on 15/5/18.
 */
public class SPCache {
    private static final String TAG = SPCache.class.getSimpleName();
    private static SPCache                                      INSTANCE;
    private        ConcurrentMap<String, SoftReference<Object>> mCache;
    private static String mPrefFileName = "TIANXIN";
    private static Context mContext;

    private SPCache(Context context, String prefFileName) {
        mContext = context.getApplicationContext();
        mCache = new ConcurrentHashMap<String, SoftReference<Object>>();
        initDatas(prefFileName);

    }

    private void initDatas(String prefFileName) {
        if (null != prefFileName && prefFileName.trim().length() > 0) {
            mPrefFileName = prefFileName;
        } else {
            Log.d(TAG, "prefFileName is invalid , we will use default value ");
        }
        //SharedPreferences sp = mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
        //Map<String, ?> alreadyDatas = sp.getAll();
        //Set<String> keys = alreadyDatas.keySet();
        //for (String key : keys)
        //{
        //    mCache.put(key, new SoftReference<Object>(alreadyDatas.get(key)));
        //}
    }

    public static SPCache init(Context context, String prefFileName) {
        if (INSTANCE == null) {
            synchronized (SPCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SPCache(context, prefFileName);
                }
            }
        }
        return INSTANCE;
    }

    public static SPCache init(Context context) {
        return init(context, null);
    }

    private static SPCache getInstance() {
        if (INSTANCE == null)
            throw new NullPointerException("you show invoke SpCache.init() before you use it ");

        return INSTANCE;
    }


    //put

    public static SPCache putInt(String key, int val) {
        return getInstance().put(key, val);
    }

    public static SPCache putLong(String key, long val) {
        return getInstance().put(key, val);
    }

    public static SPCache putString(String key, String val) {
        return getInstance().put(key, val);
    }

    public static SPCache putBoolean(String key, boolean val) {
        return getInstance().put(key, val);
    }

    public static SPCache putFloat(String key, float val) {
        return getInstance().put(key, val);
    }

    public static void putObject(String key, Object object) {
        SharedPreferences sp = mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);

        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //get
    public static int getInt(String key, int defaultVal) {
        return (int) (getInstance().get(key, defaultVal));
    }

    public static long getLong(String key, long defaultVal) {
        return (long) (getInstance().get(key, defaultVal));
    }

    public static String getString(String key, String defaultVal) {
        return (String) (getInstance().get(key, defaultVal));
    }

    public static boolean getBoolean(String key, boolean defaultVal) {
        return (boolean) (getInstance().get(key, defaultVal));
    }

    public static float getFloat(String key, float defaultVal) {
        return (float) (getInstance().get(key, defaultVal));
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        SharedPreferences sp = mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 对于外部不可见的过渡方法
     *
     * @param key
     * @param clazz
     * @param sp
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
        T t;
        try {

            t = clazz.newInstance();

            if (t instanceof Integer) {
                return (T) Integer.valueOf(sp.getInt(key, 0));
            } else if (t instanceof String) {
                return (T) sp.getString(key, "");
            } else if (t instanceof Boolean) {
                return (T) Boolean.valueOf(sp.getBoolean(key, false));
            } else if (t instanceof Long) {
                return (T) Long.valueOf(sp.getLong(key, 0L));
            } else if (t instanceof Float) {
                return (T) Float.valueOf(sp.getFloat(key, 0L));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
        }
        Log.e("system", "无法找到" + key + "对应的值");
        return null;
    }

    //contains
    public boolean contains(String key) {
        return mCache.get(key).get() != null ? true : getSharedPreferences().contains(key);
    }

    //remove
    public static SPCache remove(String key) {
        return INSTANCE._remove(key);
    }

    private SPCache _remove(String key) {
        mCache.remove(key);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    //clear
    public static SPCache clear() {
        return INSTANCE._clear();
    }

    private SPCache _clear() {
        mCache.clear();
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    private <T> SPCache put(String key, T t) {
        mCache.put(key, new SoftReference<Object>(t));
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (t instanceof String) {
            editor.putString(key, (String) t);
        } else if (t instanceof Integer) {
            editor.putInt(key, (Integer) t);
        } else if (t instanceof Boolean) {
            editor.putBoolean(key, (Boolean) t);
        } else if (t instanceof Float) {
            editor.putFloat(key, (Float) t);
        } else if (t instanceof Long) {
            editor.putLong(key, (Long) t);
        } else {
            Log.d(TAG, "you may be put a invalid object :" + t);
            editor.putString(key, t.toString());
        }

        SharedPreferencesCompat.apply(editor);
        return INSTANCE;
    }

    private Object readDisk(String key, Object defaultObject) {
        Log.e("TAG", "readDisk");
        SharedPreferences sp = getSharedPreferences();

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        Log.e(TAG, "you can not read object , which class is " + defaultObject.getClass().getSimpleName());
        return null;

    }

    private Object get(String key, Object defaultVal) {
        SoftReference reference = mCache.get(key);
        Object val = null;
        if (null == reference || null == reference.get()) {
            val = readDisk(key, defaultVal);
            mCache.put(key, new SoftReference<Object>(val));
        }
        val = mCache.get(key).get();
        return val;
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(final SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    editor.commit();
                    return null;
                }
            };
        }
    }


    private static SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
    }


}
