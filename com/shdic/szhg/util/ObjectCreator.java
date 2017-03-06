package com.shdic.szhg.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 对象bean和数据集合（Map或List）转换处理类
 * @author zangql
 *
 */
public class ObjectCreator {

  /**
   * 将数据集合的值按关键字映射到实体BEAN中；
   * 类中含有联合主键的实体类类型的属性，通过递归反射填充类的值
   * @param _className String 实体BEAN的名称，也即为数据库的表结构
   * @param _hashTable Map    包含操作数据的容器
   * @return Object           通过反射机制将容器数据填充到对象后的实体
   */
  public static Object createSpecialObjectBean(String _className,
                                               Map _hashTable) throws Exception {
    Object m_Object = null;

    try {
      /**
       * 将String类型的名称转为对象类型
       */
      m_Object = Class.forName(_className).newInstance();
      Object values[] = null;
      Method meth;
      Class typeObject = null;

      Class classObject = m_Object.getClass();
      /**
       * 获得对象实体中的所有属性，包括属性名称，属性的类型
       */
      Field fieldlist[] = classObject.getDeclaredFields();
      for (int i = 0; i < fieldlist.length; i++) {
        Field fld = fieldlist[i];
        /**
         * 循环取出一个属性，将其属性名称与HASHTABLE的键值进行比较，
         * 若在则获得属性的名字和根据属性的名字在HashTable中取值，
         * 并将Hashtable的值类型转换成属性的类型；
         * 若不在则要判断属性的类型是否为类类型，若为类类型则递归处理新的实体BEAN
         * 不为类类型则说明不在Hashtable的键值中，不处理。
         */
        if (_hashTable.containsKey(fld.getName())) {
          if (_hashTable.get(fld.getName()) != null) {
            /**
             * 获得与属性名同名的键值的值
             */

            Object colValue = _hashTable.get(fld.getName());
            /**
             * 获得属性的类型
             */

            typeObject = getClass(fld.getType().toString());
            /**
             * 将Map中的单元值转换为属性类型的值
             */

            values = getInstances(fld.getType().toString(),
                                  colValue);
            /**
             * 通过反射机制将获得以set开头的方法
             */

            meth = classObject.getMethod("set" +
                                         Tools.FormatColName(fld.getName()),
                                         new Class[] {typeObject});
            /**
             * 反射将值映射到实体中
             */

            meth.invoke(m_Object, values);
          }
        }
        else {
          String typeName = getTypeClass(fld.getType().toString());
          Object SpecialObject = null;
          if (!typeName.equals("1")) {
            /**
             * 如果属性的类型为类类型，则以递归的方式继续填充
             */

            SpecialObject = createSpecialObjectBean(typeName,
                _hashTable);
          }
          /**
           * 将递归产生的对象实体set到所属的实体中
           */
          if (SpecialObject != null) {
            typeObject = getClass(fld.getType().toString());

            values = getInstances(fld.getType().toString(),
                                  SpecialObject);

            meth = classObject.getMethod("set" +
                                         Tools.FormatColName(fld.getName()),
                                         new Class[] {typeObject});
            meth.invoke(m_Object, values);
          }
        }
      }
      return m_Object;
    }
    catch (Exception e) {
      System.out.println("error pos objectcreator createSpecialObjectBean");
      //e.printStackTrace();
      throw e;
    }
    //return null;
  }

  /**
   * 类中含有类类型的属性，对象中包含联合主键类型通过递归反射填充类的值
   * 根据传入的对象的属性，将HashTable的值重新映射到对象中
   * 用于Updata操作,即将Map中的数据更新实体BEAN
   * @param _classObject Object 实体对象，包含实体的所有信息和值
   * @param _hashTable Map
   * @return Object
   */
  public static Object createSpecialObjectBean(Object _classObject,
                                               Map _hashTable) throws Exception {
    Object m_Object = null;

    try {
      m_Object = _classObject;
      Object values[] = null;
      Method meth;
      Class typeObject = null;

      Class classObject = m_Object.getClass();

      /**
       * 获得对象实体中的所有属性，包括属性名称，属性的类型
       */
      Field fieldlist[] = classObject.getDeclaredFields();
      for (int i = 0; i < fieldlist.length; i++) {
        Field fld = fieldlist[i];
        /**
         * 循环取出一个属性，将其属性名称与HASHTABLE的键值进行比较，
         * 若在则获得属性的名字和根据属性的名字在HashTable中取值，
         * 并将Hashtable的值类型转换成属性的类型；
         * 若不在则要判断属性的类型是否为类类型，若为类类型则递归处理新的实体BEAN
         * 不为类类型则说明不在Hashtable的键值中，不处理。
         */

        if (_hashTable.containsKey(fld.getName())) {
          if (_hashTable.get(fld.getName()) != null) {
            /**
             * 获得与属性名同名的键值的值
             */
            Object colValue = _hashTable.get(fld.getName()).
                toString();
            /**
             * 获得属性的类型
             */
            typeObject = getClass(fld.getType().toString());
            /**
             * 将Map中的单元值转换为属性类型的值
             */
            values = getInstances(fld.getType().toString(),
                                  colValue);
            /**
             * 通过反射机制将获得以set开头的方法
             */
            meth = classObject.getMethod("set" +
                                         Tools.FormatColName(fld.getName()),
                                         new Class[] {typeObject});
            /**
             * 反射将值映射到实体中
             */
            meth.invoke(m_Object, values);
          }
        }
        else {
          String typeName = getTypeClass(fld.getType().toString());
          Object SpecialObject = null;
          if (!typeName.equals("1")) {
            /**对于联合主键中的对个字段的部分填充的修改 2005-9-15*/
            meth = classObject.getMethod("get" +
                                         Tools.FormatColName(fld.getName()), null);

            /**将按属性的名称将其值取出*/
            Object objectValue_UnitKey = meth.invoke(m_Object, null);

            /****************************************/

            /**
             * 如果属性的类型为类类型，则以递归的方式继续填充
             */
            SpecialObject = createSpecialObjectBean(objectValue_UnitKey,
                _hashTable);
          }
          /**
           * 将递归产生的对象实体set到所属的实体中
           */
          if (SpecialObject != null) {
            typeObject = getClass(fld.getType().toString());

            values = getInstances(fld.getType().toString(),
                                  SpecialObject);

            meth = classObject.getMethod("set" +
                                         Tools.FormatColName(fld.getName()),
                                         new Class[] {typeObject});
            meth.invoke(m_Object, values);
          }

        }
      }
      return m_Object;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    // return null;
  }

  /**
   * 根据传入对象实体BEAN的结构，映射生成HashTable返回，做为查询时使用
   * 将查询生成的实体对象，按属性格式生成Map返回
   * @param _classObject Object 通过查询生成的对象实体
   * @param _hashTable Map      要封装数据的容器结构
   * @return Map                已经通过反射机制将对象的数据填充到容器中
   */

  public static Map createSpecialMap(Object _classObject,
                                     Map _hashTable) throws Exception {
    Object m_Object = null;

    try {
      m_Object = _classObject;
      Object values[] = null;

      Method meth;
      /**
       * 将对象类型的转换为Class类型
       */
      Class classObject = m_Object.getClass();
      Field fieldlist[] = classObject.getDeclaredFields();
      for (int i = 0; i < fieldlist.length; i++) {
        Field fld = fieldlist[i];

        /**
         * 判断此属性的类型是否是基本的数据类型，若不是则根据属性的类类型再一次递归找出此类下所有的属性
         * 并将其值取出后填充到Map中
         */
        String typeName = getTypeClass(fld.getType().toString());

        if (!typeName.equals("1")) {
          /**
           * 取得以get开头的属性方法
           */
          meth = classObject.getMethod("get" + Tools.FormatColName(fld.getName()), null);
          /**
           * 将属性的值取出
           */
          Object objectSingleValue = meth.invoke(m_Object, values);
          /**
           * 将联合主键的对象实体中的属性值依次取出后填充Map
           * @param <any> objectSingleValue
           */
          createSpecialMap(objectSingleValue, _hashTable);
        }
        else {
          /**
           * 非联合主键的处理方式
           */
          if (!fld.getName().equals("__hashCodeCalc") &&
              !fld.getName().equals("__equalsCalc")) {
            meth = classObject.getMethod("get" +
                                         Tools.FormatColName(fld.getName()), null);

            /**
             * 将属性的值取出
             */
            Object objectValue = meth.invoke(m_Object, values);

            if (objectValue != null) {
              /**
               * 将属性名称和值做为单元填充Map
               */
              _hashTable.put(fld.getName(), objectValue);
            }
            else {
              _hashTable.put(fld.getName(), "");
            }
          }
        }
      }
      return _hashTable;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    // return null;
  }

  /**
   * 根据实体BEAN的名称获得其中联合主键的实体对象，并将其填充后返回
   * @param _className String  实体的名称
   * @param _hashTable Map     含有值的Hashtable
   * @return Object            仅含有联合主键对象实体的对象
   */
  public static Object CreateUniteKeyObjectBean(String _className,
                                                Map _hashTable) throws
      Exception {
    Object m_Object = null;

    try {
      /**
       * 将字符串转换为CLASS类型
       */
      m_Object = Class.forName(_className).newInstance();

      Class classObject = m_Object.getClass();
      /**
       * 获得对象实体中的所有属性，包括属性名称，属性的类型
       */

      Field fieldlist[] = classObject.getDeclaredFields();
      for (int i = 0; i < fieldlist.length; i++) {
        /**
         * 取出其中的一个属性
         */
        Field fld = fieldlist[i];
        /**
         * 判其是否是类类型的属性
         */
        String typeName = getTypeClass(fld.getType().toString());

        if (typeName.equals("1")) {
          if (_hashTable.containsKey(fld.getName())) {
            return null;
          }
        }
        /**
         * 如果是类类型的属性将进行字段比较后将Map中的数据填充实体
         */
        if (!typeName.equals("1")) {
          boolean flog = false;
          Object uniteKeyObject = null;
          uniteKeyObject = Class.forName(typeName).newInstance();
          Class uniteKeyClass = uniteKeyObject.getClass();
          Field uniteKeyField[] = uniteKeyClass.getDeclaredFields();
          for (int j = 0; j < fieldlist.length; j++) {
            Field uniteKeyFld = uniteKeyField[i];
            if (!_hashTable.containsKey(uniteKeyFld.getName())) {
              flog = true;
              return null;
            }
          }
          if (!flog) {
            m_Object = CreateSingleObjectBean(typeName, _hashTable);
          }
        }
      }
      return m_Object;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    //return null;

  }

  /**
   * 将Map中的数据反射机制映射到实体中
   * @param _className String
   * @param _hashTable Map
   * @return Object
   */
  private static Object CreateSingleObjectBean(String _className,
                                               Map _hashTable) throws Exception {
    Object m_Object = null;

    try {
      m_Object = Class.forName(_className).newInstance();
      Object values[] = null;
      Method meth;
      Class typeObject = null;
      for (Iterator it = _hashTable.keySet().iterator(); it.hasNext(); ) {
        String itKey = it.next().toString();
        String colName = itKey;
        Object colValue = _hashTable.get(colName).toString();

        if (colValue != null) {
          Class classObject = m_Object.getClass();
          Field fieldlist[] = classObject.getDeclaredFields();
          for (int i = 0; i < fieldlist.length; i++) {
            Field fld = fieldlist[i];
            if (colName.equals(fld.getName())) {

              typeObject = getClass(fld.getType().toString());
              values = getInstances(fld.getType().toString(), colValue);

              meth = classObject.getMethod("set" + Tools.FormatColName(colName),
                                           new Class[] {typeObject});
              meth.invoke(m_Object, values);

            }
          }
        }
      }
      return m_Object;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    //return null;
  }

  /**
   * 根据传入的Map分别判断每一个键值属性是否为联合主键，若是则加上联合主键的对象名，否则直接返回
   * 作用是做查询时使用，将查询条件以及其值作为Map，返回时若是联合主键则将键值前加上主键对象名
   * @param _className String   实体的名称
   * @param _hashTable Map      查询条件的条件名称以及值
   * @return Map                处理后的Map
   */

  public static Map FileNameBean(String _className, Map _hashTable) throws
      Exception {

    Object m_Object = null;
    String specialKeyName = "";
    String specialKeyTypeName = "";
    /***/
    Object instanceValue = null;

    LinkedHashMap fileNameTable = new LinkedHashMap();
    try {
      m_Object = Class.forName(_className).newInstance();
      Class classObject = m_Object.getClass();
      Field fieldlist[] = classObject.getDeclaredFields();
      /**
       * 获得联合主键的名称
       */
      for (int i = 0; i < fieldlist.length; i++) {
        Field fld = fieldlist[i];
        if (!getTypeClass(fld.getType().toString()).equals("1")) {
          /**获得联合主键的类型名即类名*/
          specialKeyName = fld.getName();
          specialKeyTypeName = fld.getType().toString();
        }
      }
      /**
       * 循环将Map中的键值名称进行判断是否是联合主键的属性；
       * 若是则将名称前加上联合主键的名称后重新存入Map
       */
      for (Iterator it = _hashTable.keySet().iterator(); it.hasNext(); ) {
        String itKey = it.next().toString();
        String colName = itKey;
        //System.out.println("++++++++++++++++++"+colName);
        Object colValue = null;
        if (_hashTable.get(colName) != null) {
          colValue = _hashTable.get(colName).toString();
        }
        String flog = "";
        Field fld = null;

        for (int i = 0; i < fieldlist.length; i++) {
          fld = fieldlist[i];
          if (getTypeClass(fld.getType().toString()).equals("1")) {
            /**判是否集合中的键值是否是基本数据类型的属性名*/
            if (colName.equals(fld.getName())) {

              instanceValue = getInstancesType(fld.getType().toString(),
                                               colValue);
              fileNameTable.put(colName, instanceValue);
              flog = "1";
              break;
            }
            /**HashMap中包含着Bean结构中没有的属性*/

          }
          else {
            flog = "2";
          }

        }
        if (flog.equals("2")) {

          /**在联合主键所属的类中继续查询联合主键类中的字段*/
          //  System.out.println("KKKKKKKKKKKKKK"+specialKeyTypeName );
          Object m_key_Object = Class.forName(specialKeyTypeName.substring(6)).
              newInstance();
          Class class_key_Object = m_key_Object.getClass();
          Field field_key_list[] = class_key_Object.getDeclaredFields();
          Field fld_key = null;

          for (int i = 0; i < field_key_list.length; i++) {
            fld_key = field_key_list[i];
            if (colName.equals(fld_key.getName())) {

              instanceValue = getInstancesType(fld_key.getType().toString(),
                                               colValue);
            }
          }

          String keyName = specialKeyName + "." + colName;
          fileNameTable.put(keyName, instanceValue);
        }

      }
      return fileNameTable;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    //return null;
  }

  public static String GainSpecialFileName(String className, String fileName) throws
      Exception {

    String SpecialFileName = "";
    Object m_Object = null;
    try {
      m_Object = Class.forName(className).newInstance();
      Class classObject = m_Object.getClass();
      Field fieldlist[] = classObject.getDeclaredFields();
      /**
       * 获得联合主键的名称
       */
      for (int i = 0; i < fieldlist.length; i++) {
        Field fld = fieldlist[i];
        /**  依次判断结构体的字段名和传入的字段名是否一致 */
        if (fld.getName().equals(fileName) &&
            getTypeClass(fld.getType().toString()).equals("1")) {

          SpecialFileName = fileName;

        }
        /**  如不一致则判断结构体的字段名是否是联合主键，
         *   如果是联合主键则继续判断联合主键的字段是否和传入的字段一致 */
        else if (!getTypeClass(fld.getType().toString()).equals("1")) {
          String specialKeyTypeName = fld.getType().toString().substring(6);
          String specialKeyFileName = GainSpecialFileName(specialKeyTypeName,
              fileName);
          if (specialKeyFileName != null && !specialKeyFileName.equals("")) {
            SpecialFileName = "id." + specialKeyFileName;
          }
        }
      }
    }
    catch (Exception ex) {

      ex.printStackTrace();
    }
    return SpecialFileName;
  }

  /**
   * 判断传过来的类型是否为基本类型，若不是则返回类型的名称，即类的名称
   * @param className String  描述类型的名称
   * @throws ClassNotFoundException
   * @return String
   */
  private static String getTypeClass(String className) throws
      Exception {
    try {
      String temp = "";
      if (className.equals("boolean")) {
        temp = "1";
      }
      else if (className.equals("class [Ljava.lang.Byte;")) {
        temp = "1";
      }
      else if (className.equals("class [B")) {
        temp = "1";
      }
      else if (className.equals("char")) {
        temp = "1";
      }
      else if (className.equals("double")) {
        temp = "1";
      }
      else if (className.equals("float")) {
        temp = "1";
      }
      else if (className.equals("int")) {
        temp = "1";
      }
      else if (className.equals("long")) {
        temp = "1";
      }
      else if (className.equals("short")) {
        temp = "1";
      }
      else if (className.equals("class java.lang.String")) {
        temp = "1";
      }
      else if (className.equals("class java.lang.Double")) {
        temp = "1";
      }
      else if (className.equals("class java.lang.Integer")) {
        temp = "1";
      }
      else if (className.equals("class java.lang.Long")) {
        temp = "1";
      }
      else if (className.equals("class java.lang.Float")) {
        temp = "1";
      }
      else if (className.equals("class java.sql.Date")) {
        temp = "1";
      }
      else if (className.equals("class java.util.Date")) {
       temp = "1";
     }
      else if (className.equals("class java.sql.Blob")) {
        temp = "1";
      }
      else if (className.equals("class java.lang.Object")) {
        temp = "1";
      }
      else {
        temp = className.substring(6);
      }
      return temp;
    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 获得ClassName描述的数据类型
   * @param className String   描述类型的名称
   * @throws ClassNotFoundException
   * @return Class
   */
  private static Class getClass(String className) throws
      Exception {
    try {
      Class temp = null;
      if (className.equals("boolean")) {
        temp = Boolean.TYPE;
      }
      else if (className.equals("class [Ljava.lang.Byte;")) {
        temp = Class.forName("[Ljava.lang.Byte;");
      }
      else if (className.equals("char")) {
        temp = Character.TYPE;
      }
      else if (className.equals("double")) {
        temp = Double.TYPE;
      }
      else if (className.equals("float")) {
        temp = Float.TYPE;
      }
      else if (className.equals("int")) {
        temp = Integer.TYPE;
      }
      else if (className.equals("long")) {
        temp = Long.TYPE;
      }
      else if (className.equals("short")) {
        temp = Short.TYPE;
      }
      else if (className.equals("class java.lang.String")) {
        temp = Class.forName("java.lang.String");
      }
      else if (className.equals("class java.lang.Double")) {
        temp = Class.forName("java.lang.Double");
      }
      else if (className.equals("class java.lang.Integer")) {
        temp = Class.forName("java.lang.Integer");
      }
      else if (className.equals("class java.lang.Long")) {
        temp = Class.forName("java.lang.Long");
      }
      else if (className.equals("class java.lang.Float")) {
        temp = Class.forName("java.lang.Float");
      }
      else if (className.equals("class java.sql.Date")) {
        temp = Class.forName("java.sql.Date");
      }
      else if (className.equals("class java.sql.Blob")) {
        temp = Class.forName("java.sql.Blob");
      }
      else if (className.equals("class java.util.Date")) {
       temp = Class.forName("java.util.Date");
     }
      else if (className.equals("interface java.sql.Blob")) {
        temp = Class.forName("java.sql.Blob");
      }
      else {
        temp = Class.forName(className.substring(6));
      }
      return temp;
    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 将字符串类型的数据转为 ClassName要求的数据格式
   * 返回值是Object[]类型
   * @param className String      类型的字符描述
   * @param valueObject Object    对象类型的对象值
   * @return Object[]
   */
  private static Object[] getInstances(String className, Object valueObject) throws
      Exception {
    try {
      if (className == null) {
        return null;
      }
      Object[] tempValue = null;

      String value = "";

      if (valueObject != null) {

        value = valueObject.toString();
      }
      else {
        value = "";
      }

      if (className.equals("boolean")) {
        tempValue = (new Object[] {Boolean.valueOf(value)});
      }
      else if (className.equals("class [Ljava.lang.Byte;")) {

        //   tempValue = (new Object[] {valueObject});
        byte[] b = (byte[]) valueObject;
        tempValue = (new Object[] {b});

      }
      else if (className.equals("char")) {
        tempValue = (new Object[] {new Character(value.toCharArray()[0])});
      }
      else if (className.equals("double")) {
        if (value.equals("")) {
          value = null;
          tempValue = (new Object[] {null});
        }
        else {
          tempValue = (new Object[] {Double.valueOf(value)});
        }

      }
      else if (className.equals("float")) {

        if (value.equals("")) {
          value = null;
          tempValue = (new Object[] {null});
        }
        else {
          tempValue = (new Object[] {Float.valueOf(value)});
        }

      }
      else if (className.equals("int")) {

        if (value.equals("")) {
          value = null;
          tempValue = (new Object[] {null});
        }
        else {
          tempValue = (new Object[] {Integer.valueOf(value)});
        }
      }
      else if (className.equals("long")) {

        if (value.equals("")) {
          value = null;
          tempValue = (new Object[] {null});
        }
        else {
          tempValue = (new Object[] {Long.valueOf(value)});
        }
      }
      else if (className.equals("short")) {
        tempValue = (new Object[] {Short.valueOf(value)});
      }
      else {
        try {
          if (className.equals("class java.lang.String")) {
            tempValue = (new Object[] {value});
          }
          else if (className.equals("class java.lang.Double")) {
            if (value.equals("")) {
              value = null;
              tempValue = (new Object[] {null});
            }
            else {
              tempValue = (new Object[] {Double.valueOf(value)});
            }
          }
          else if (className.equals("class java.lang.Integer")) {
            if (value.equals("")) {
              value = null;
              tempValue = (new Object[] {null});
            }
            else {
              tempValue = (new Object[] {Integer.valueOf(value)});
            }
          }
          else if (className.equals("class java.lang.Long")) {

            if (value.equals("")) {
              value = null;
              tempValue = (new Object[] {null});
            }
            else {
              tempValue = (new Object[] {Long.valueOf(value)});
            }
          }
          else if (className.equals("class java.lang.Float")) {
            if (value.equals("")) {
              value = null;
              tempValue = (new Object[] {null});
            }
            else {
              tempValue = (new Object[] {Float.valueOf(value)});
            }
          }
          else if (className.equals("class java.sql.Date")) {
            /** 关于日期的长度还是有一些问题 */
            tempValue = (new Object[] {Tools.cDate(value)});
          }
          else if (className.equals("class java.util.Date")) {
            /** 关于日期的长度还是有一些问题 */
            tempValue = (new Object[] {Tools.cDate(value)});
          }
          else if (className.equals("class java.sql.Blob")) {
            tempValue = (new Object[] {valueObject});
          }
          else {
            tempValue = (new Object[] {valueObject});
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      return tempValue;
    }
    catch (Exception e) {
      throw e;
    }
  }

  /**
   * 将字符串类型的数据转为 ClassName要求的数据格式
   * 返回值是Object类型
   * @param className String      类型的字符描述
   * @param valueObject Object    对象类型的对象值
   * @return Object[]
   */

  private static Object getInstancesType(String className, Object valueObject) throws
      Exception {
    try {
      if (className == null) {
        return null;
      }
      Object tempValue = null;

      String value = "";

      if (valueObject != null) {

        value = valueObject.toString();
      }
      else {
        value = "";
      }

      if (className.equals("boolean")) {
        tempValue = Boolean.valueOf(value);
      }
      else if (className.equals("class [B")) {
        //tempValue = valueObject.toString().getBytes();
        try {
          tempValue = new sun.misc.BASE64Decoder().decodeBuffer(valueObject.
              toString());
        }
        catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      else if (className.equals("char")) {
        tempValue = new Character(value.toCharArray()[0]);
      }
      else if (className.equals("double")) {
        if (value.equals("")) {
          value = null;
          tempValue = null;
        }
        else {
          tempValue = Double.valueOf(value);
        }

        //   tempValue = Double.valueOf(value);
      }
      else if (className.equals("float")) {

        if (value.equals("")) {
          value = null;
          tempValue = null;
        }
        else {
          tempValue = Float.valueOf(value);
        }

        //tempValue = Float.valueOf(value);
      }
      else if (className.equals("int")) {

        if (value.equals("")) {
          value = null;
          tempValue = null;
        }
        else {
          tempValue = Integer.valueOf(value);
        }
        //   tempValue = Integer.valueOf(value);
      }
      else if (className.equals("long")) {

        if (value.equals("")) {
          value = null;
          tempValue = null;
        }
        else {
          tempValue = Long.valueOf(value);
        }

        //   tempValue = Long.valueOf(value);
      }
      else if (className.equals("short")) {
        tempValue = Short.valueOf(value);
      }
      else {
        try {
          if (className.equals("class java.lang.String")) {
            tempValue = value;
          }
          else if (className.equals("class java.lang.Double")) {

            if (value.equals("")) {
              value = null;
              tempValue = null;
            }
            else {
              tempValue = Double.valueOf(value);
            }
            // tempValue = Double.valueOf(value);
          }
          else if (className.equals("class java.lang.Integer")) {

            if (value.equals("")) {
              value = null;
              tempValue = null;
            }
            else {
              tempValue = Integer.valueOf(value);
            }
            //  tempValue = Integer.valueOf(value);
          }
          else if (className.equals("class java.lang.Long")) {
            if (value.equals("")) {
              value = null;
              tempValue = null;
            }
            else {
              tempValue = Long.valueOf(value);
            }
            // tempValue = Long.valueOf(value);
          }
          else if (className.equals("class java.lang.Float")) {
            if (value.equals("")) {
              value = null;
              tempValue = null;
            }
            else {
              tempValue = Float.valueOf(value);
            }
            //tempValue = Float.valueOf(value);
          }
          else if (className.equals("class java.sql.Date")) {
            tempValue = Tools.cDate(value);
          }

          else if (className.equals("class java.util.Date")) {
           tempValue = Tools.cDate(value);
         }

          else {
            tempValue = valueObject;
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      return tempValue;
    }
    catch (Exception e) {
      throw e;
    }
  }

}
