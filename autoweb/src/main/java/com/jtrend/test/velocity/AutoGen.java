package com.jtrend.test.velocity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seox.util.PropertiesLoader;

public class AutoGen {
    
    PropertiesLoader m_props;

    public AutoGen() {
        
//        this("gen.properties");
        this("gen-TEST2.properties"); 
//        this("gen-STYLES.properties"); 
//       this("gen-CLEANER.properties");
//      this("gen-church.properties");
//        this("gen-poll.properties");
//        this("gen-test2.properties"); // This file is test re
//        this("gen-test.properties", "gen-sweep-file.properties");
    }

    public AutoGen(String genProps) {
        String appRootDir = "velocity/";
        String appConfigDir = "velocity/templates/";

        // ######################################################################################
        // DS Generation

        String genPropertyFile = genProps; // "gen.properties";
        String filePropertyFile = genProps;

        boolean createDbGen = true;
        boolean createFileGen = false;

        PropertiesLoader allProps = new PropertiesLoader(appConfigDir + genPropertyFile);
        PropertiesLoader defaultProps = new PropertiesLoader(appConfigDir + "gen-DEFAULTS.properties");
        PropertiesLoader fileProps = new PropertiesLoader(appConfigDir + filePropertyFile);
        
        String dsGenList = allProps.getProperty("ds.list");
        String fileFormList = fileProps.getProperty("form.list"); // from file
        
        removeDupsFrom(allProps, defaultProps);
        allProps.putAll(defaultProps);

        printProperties(allProps);
        
        m_logger.debug("GEN LIST: "+dsGenList);
        //m_logger.debug(fileFormList);

        StringTokenizer tokenizer = new StringTokenizer(dsGenList, ", ");

        while (createDbGen && tokenizer.hasMoreTokens()) {

            String appName = tokenizer.nextToken().trim();
            
            String iosRoot = allProps.getProperty(appName +".ios.root");
                    
            
            Properties subProps = getSubProperties(allProps, appName);
            Properties extentProps = getSubProperties(allProps, appName + ".extent", false);

            checkProperties(appName, subProps);

            m_logger.debug("subProps=" + subProps);
            m_logger.debug("extentProps=" + extentProps);

            List inputFields = getInputFields(subProps, extentProps);
            m_logger.debug("###### " + inputFields);

            List allInputFields = getInputFields(subProps, extentProps, false, false);
            m_logger.debug("###### " + allInputFields);

            
            //Create Sort fields for IOS files
            
            List inpurtSortedFields = new ArrayList(inputFields);
            Collections.sort(inpurtSortedFields, new Comparator<Map>() {
                public int compare(Map map1, Map map2){
                    
                    String title1 = (String) map1.get("title");
                    String title2 = (String) map2.get("title");
                    
                    return title1.compareToIgnoreCase(title2);
                }
            });
            
            subProps.put("inputFields", inputFields);
            subProps.put("inpurtSortedFields", inpurtSortedFields);
            subProps.put("allInputFields", allInputFields);

            printPropertiesInFormat(inputFields);
            
            
            CreateDS2 ds            = new CreateDS2(subProps, appRootDir, appName, "ds");
            CreateDS2 dsext         = new CreateDS2(subProps, appRootDir, appName, "ds_ext");

            CreateDS2 action        = new CreateDS2(subProps, appRootDir, appName, "action");
            CreateDS2 actionform    = new CreateDS2(subProps, appRootDir, appName, "action_form");
            CreateDS2 ajaxaction    = new CreateDS2(subProps, appRootDir, appName, "action_ajax");
            CreateDS2 actionextent  = new CreateDS2(subProps, appRootDir, appName, "action_extent");

            CreateDS2 form          = new CreateDS2(subProps, appRootDir, appName, "form_home");
            CreateDS2 formlist      = new CreateDS2(subProps, appRootDir, appName, "form_list");
            CreateDS2 formajax      = new CreateDS2(subProps, appRootDir, appName, "form_ajax");
            CreateDS2 formaddedit   = new CreateDS2(subProps, appRootDir, appName, "form_addedit");

            CreateDS2 form_mobile          = new CreateDS2(subProps, appRootDir, appName, "form_home_mobile");
            CreateDS2 formlist_mobile      = new CreateDS2(subProps, appRootDir, appName, "form_list_mobile");
            CreateDS2 formajax_mobile      = new CreateDS2(subProps, appRootDir, appName, "form_ajax_mobile");
            CreateDS2 formaddedit_mobile   = new CreateDS2(subProps, appRootDir, appName, "form_addedit_mobile");
            
            CreateDS2 form_partition   = new CreateDS2(subProps, appRootDir, appName, "form_partition");
            
            
            CreateDS2 viewproc      = new CreateDS2(subProps, appRootDir, appName, "viewproc");
            CreateDS2 struts        = new CreateDS2(subProps, appRootDir, appName, "struts");
            CreateDS2 dbo           = new CreateDS2(subProps, appRootDir, appName, "dbo");

            if ( subProps.containsKey("useDbTable") && !subProps.get("useDbTable").equals("")) {
                CreateDS2 dao           = new CreateDS2(subProps, appRootDir, appName, "dao");
                CreateDS2 dao_extent           = new CreateDS2(subProps, appRootDir, appName, "dao_extent");
            }
            
            CreateDS2 dataobj       = new CreateDS2(subProps, appRootDir, appName, "dataobj"); //Data Holder
            CreateDS2 example       = new CreateDS2(subProps, appRootDir, appName, "example");

            //IOS FILES
            CreateDS2 ios_entity_header     = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_entity_header");
            CreateDS2 ios_entity_source     = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_entity_source");
            CreateDS2 ios_dto_header     = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_dto_header");
            CreateDS2 ios_dto_source     = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_dto_source");
            CreateDS2 ios_do_header         = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_do_header");
            CreateDS2 ios_do_source         = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_do_source");
            CreateDS2 ios_snippet           = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_snippet");
            CreateDS2 ios_script           = new CreateDS2(subProps, "c:\\shared\\", appName, "ios_copyscript");
        }

        if (true) return;
        
        StringTokenizer tokenizer2 = new StringTokenizer(fileFormList, ", ");

        while (createFileGen && tokenizer2.hasMoreTokens()) {
            String appName = tokenizer2.nextToken();
            Properties subProps = getSubProperties(fileProps, appName);

            List inputFields = getInputFields(subProps);
            m_logger.debug("###### " + inputFields);
            List allInputFields = getInputFields(subProps, null, false, false);

            subProps.put("inputFields", inputFields);
            subProps.put("allInputFields", allInputFields);

            CreateDS2 ds = new CreateDS2(subProps, appRootDir, appName, "ds");
            CreateDS2 form = new CreateDS2(subProps, appRootDir, appName, "form");
            CreateDS2 formlist = new CreateDS2(subProps, appRootDir, appName, "formlist");
            CreateDS2 formaddcss = new CreateDS2(subProps, appRootDir, appName, "formaddcss");
            CreateDS2 formeditcss = new CreateDS2(subProps, appRootDir, appName, "formeditcss");
            CreateDS2 action = new CreateDS2(subProps, appRootDir, appName, "action");
            CreateDS2 actionextent = new CreateDS2(subProps, appRootDir, appName, "actionextent");
            CreateDS2 actionform = new CreateDS2(subProps, appRootDir, appName, "actionform");
            CreateDS2 struts = new CreateDS2(subProps, appRootDir, appName, "struts");
            CreateDS2 dbo = new CreateDS2(subProps, appRootDir, appName, "dbo");
        }

    }


    public static String convertDbTypeToJava(String dbType) {
        if (dbType.equalsIgnoreCase("VARCHAR")) {
            return "String";
        }
        else if (dbType.equalsIgnoreCase("BIGINT")) {
            return "Long";
        }
        else if (dbType.equalsIgnoreCase("INTEGER")) {
            return "Integer";
        }
        else if (dbType.equalsIgnoreCase("DATETIME")) {
            return "Timestamp";
        }
        else if (dbType.equalsIgnoreCase("DECIMAL")) {
            return "Double";
        }
        else if (dbType.equalsIgnoreCase("DATETIME")) {
            return "Timestamp";
        }
        else {
            // Just in case DB type input as Java type
            
            if (dbType.equals("int")) return "Integer";
            
            if (dbType.equalsIgnoreCase("String") ||
                    dbType.equalsIgnoreCase("Long") ||
                    dbType.equalsIgnoreCase("Date") ||
                    dbType.equalsIgnoreCase("Double") ||
                    dbType.equalsIgnoreCase("Integer")) {
                return dbType;
            }
            
            return "String";
        }
    }

    public static String convertDbTypeToJavaRaw(String dbType) {
        if (dbType.equalsIgnoreCase("VARCHAR")) {
            return "String";
        }
        else if (dbType.equalsIgnoreCase("BIGINT")) {
            return "long";
        }
        else if (dbType.equalsIgnoreCase("INTEGER")) {
            return "int";
        }
        else if (dbType.equalsIgnoreCase("DATETIME")) {
            return "Timestamp";
        }
        else if (dbType.equalsIgnoreCase("DECIMAL")) {
            return "double";
        }
        else if (dbType.equalsIgnoreCase("DATETIME")) {
            return "Timestamp";
        }
        else {

            if (dbType.equalsIgnoreCase("String") ||
                    dbType.equalsIgnoreCase("long") ||
                    dbType.equalsIgnoreCase("Date") ||
                    dbType.equalsIgnoreCase("double") ||
                dbType.equalsIgnoreCase("int")) {
                return dbType;
            }
            
            return "String";
        }
    }

    public static String convertDbTypeToIOS(String dbType) {
        if (dbType.equalsIgnoreCase("VARCHAR")) {
            return "NSString *";
        }
        else if (dbType.equalsIgnoreCase("BIGINT")) {
            return "NSNumber *";
        }
        else if (dbType.equalsIgnoreCase("INTEGER")) {
            return "NSNumber *";
        }
        else if (dbType.equalsIgnoreCase("DATETIME")) {
            return "NSDate *";
        }
        else if (dbType.equalsIgnoreCase("DECIMAL")) {
            return "NSDecimalNumber *";
        }
        else {
            return "NSString *";
        }
    }    
    public static String convertDbTypeToIOSBase(String dbType) {
        if (dbType.equalsIgnoreCase("VARCHAR")) {
            return "NSString";
        }
        else if (dbType.equalsIgnoreCase("BIGINT")) {
            return "NSNumber";
        }
        else if (dbType.equalsIgnoreCase("INTEGER")) {
            return "NSNumber";
        }
        else if (dbType.equalsIgnoreCase("DATETIME")) {
            return "NSDate";
        }
        else if (dbType.equalsIgnoreCase("DECIMAL")) {
            return "NSDecimalNumber";
        }
        else {
            return "NSString";
        }
    }    
    
    public List getInputFields(Properties props) {
        return getInputFields(props, null, true, false);
    }

    public List getInputFields(Properties props, Properties extentProps) {
        return getInputFields(props, extentProps, true, false);
    }

    public List getInputFields(Properties props, Properties extentProps, boolean filterOutMainAndSiteId, boolean filterOutAllId) {
        ArrayList inputFields = new ArrayList();

        // Prepare boolean/date/id fields

        Set booleanFields = getSet(props.getProperty("list.booleanFields"));
        Set radioFields = getSet(props.getProperty("list.radioFields"));
        Set dateFields = getSet(props.getProperty("list.dateFields"));
        Set idFields = getSet(props.getProperty("list.idFields"));
        Set checkboxFields = getSet(props.getProperty("list.checkboxFields"));
        Set hiddenFields = getSet(props.getProperty("list.hiddenFields"));
        Set requiredFields = getSet(props.getProperty("list.requiredFields"));
        Set passwordFields = getSet(props.getProperty("list.passwordFields"));
        Set textAreaFields = getSet(props.getProperty("list.textAreaFields"));
        Set wyswygTextAreaFields = getSet(props.getProperty("list.wyswygTextAreaFields"));
        Set dropdownFields = getSet(props.getProperty("list.dropdownFields"));
        Set dropdownAutoLoadFields = getSet(props.getProperty("list.dropdownAutoLoadFields"));
        Set formIgnoreFields = getSet(props.getProperty("list.formIgnoreFields"));
        Set immutableFields = getSet(props.getProperty("list.immutableFields"));
        Set autosetDateFields = getSet(props.getProperty("list.autosetDateFields"));
        Set autosetCreateDateFields = getSet(props.getProperty("list.autosetNewDateFields"));
        Set autosetUpdateDateFields = getSet(props.getProperty("list.autosetUpdateDateFields"));
        Set noPersistFields = getSet(props.getProperty("list.noPersistFields"));
        Set ajaxFormFields = getSet(props.getProperty("list.ajax_form_fields"));
        Set dsShortcutFields = getSet(props.getProperty("list.dsShortcutFields")); // this fields will be added to DS object to create id to value short cut method
        Set iosSkips = getSet(props.getProperty("list.iosSkipFields"));

        boolean ajaxFieldsSpecified = (props.getProperty("list.ajax_form_fields") == null || props.getProperty("list.ajax_form_fields").trim().equals("")) ? false : true;

        Map sizeConfigs = getMap(props.getProperty("list.sizeConfigs"));
        Map displayConfigs = getMap(props.getProperty("list.displayConfigs"));
        Map dropdownOptions = getMap(props.getProperty("list.dropdownOptions"), GETMAP_TYPE_VALUE_AS_LIST);
        Map dropdownAutoLoadOptions = getMap(props.getProperty("list.dropdownAutoLoadOptions"), GETMAP_TYPE_FOR_AUTOLOAD_OPTION);
        Map dropdownAutoLoadKey = getMap(props.getProperty("list.dropdownAutoLoadKey"));
        Map twoKeyUniqueConfigs = getMap(props.getProperty("list.displayConfigs"));
        Map defaultValues = getMap(props.getProperty("list.defaultValues"));
        Map dependencyIntegrityChecks = getMap(props.getProperty("list.depenencyIntegrityCheck"));

        //IOS only
        
        m_logger.debug("############################ " + booleanFields);

        String tableName = props.getProperty("useDbTable");

        List tableColumnData = null;

        if (!isNull(tableName)) {
            m_logger.debug("Getting table infor for " + tableName);

            try {
                tableColumnData = new DBLoader().getTableInfo(tableName);
                m_logger.debug(tableColumnData.toString());
            }
            catch (Exception e) {
                m_logger.error("", e);
            }
        }
        else {
            tableColumnData = getDataFrom(props);
            m_logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            m_logger.debug(tableColumnData.toString());
            m_logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        // Processing extra fields. These feilds are listed in xx..customlist.extra_fields not from database table columns
        // Then will be added to other columns
        List extraFields = (List) props.get("extra_fields");
        List extraFieldsColumns = new ArrayList();
        
        if ( extraFields != null ) {
            for (Iterator iterator = extraFields.iterator(); iterator.hasNext();) {
                Map fm = (Map) iterator.next();
                
                
                ColumnData cd = new ColumnData();
                cd.name         = (String) fm.get("NAME"); 
                cd.display      = (String) fm.get("NAME_EXTENT_1");
                cd.size         = fm.containsKey("NAME_EXTENT_2") && fm.get("NAME_EXTENT_2").toString().length()>0? Integer.parseInt((String)fm.get("NAME_EXTENT_2")): 0;
                cd.typeName     = (String) fm.get("FORMATTED_VALUES"); ;
                cd.className    = "";
                
                noPersistFields.add(cd.name);
                extraFieldsColumns.add(cd);
                
                cd.fromDb = false;
            }
        }
        tableColumnData.addAll(extraFieldsColumns);
        //
        for (Iterator iterator = tableColumnData.iterator(); iterator.hasNext();) {
            ColumnData columnData = (ColumnData) iterator.next();

            Map fieldprops = new HashMap();

            if (filterOutMainAndSiteId) {
                if (columnData.name.equals("id") || columnData.name.equals("site_id")) {
                    m_logger.debug("## skipping main and site id by option " + columnData.name);
                    continue;
                }
            }

            if (filterOutAllId) {
                if (columnData.name.equals("id") || columnData.name.endsWith("_id")) {
                    m_logger.debug("## skipping all id by option " + columnData.name);
                    continue;

                }
            }

            // Fill from the extens
            if (extentProps != null) {
                if (extentProps.get(columnData.name + ".size") != null) {
                    columnData.size = Integer.parseInt((String) extentProps.get(columnData.name + ".size"));
                }

                if (extentProps.get(columnData.name + ".display") != null) {
                    columnData.display = (String) extentProps.getProperty(columnData.name + ".display");
                }
            }

            fieldprops.put("title", columnData.name); // field_example
            fieldprops.put("prop", columnData.name); // field_example
            fieldprops.put("titleInUpper", columnData.name.toUpperCase()); // FIELD_EXAMPLE
            fieldprops.put("name", convertDbFieldToJava(columnData.name)); // FieldExample
            fieldprops.put("name2", convertDbFieldToJava(columnData.name, false)); // fieldExample
            fieldprops.put("javaName", convertDbFieldToJava(columnData.name, false)); // fieldExample
            fieldprops.put("type", convertDbTypeToJava(columnData.typeName)); // Object
            fieldprops.put("javaType", convertDbTypeToJavaRaw(columnData.typeName)); // Primitive
            fieldprops.put("iosTypeBase", convertDbTypeToIOSBase(columnData.typeName)); // Primitive
            fieldprops.put("iosType", convertDbTypeToIOS(columnData.typeName)); // Primitive
            fieldprops.put("display", convertDbFieldToJava(columnData.name, true, true)); // Field Example
            fieldprops.put("displayInUpper", convertDbFieldToJava(columnData.name, true, true).toUpperCase()); // FIELD EXAMPLE
            fieldprops.put("displayInLower", convertDbFieldToJava(columnData.name, true, true).toLowerCase()); // FIELD EXAMPLE

            
            if ( columnData.fromDb ) {
                fieldprops.put("isDbField", "true");
            } else {
                fieldprops.put("isDbField", "false");
            }
            
            if (columnData.size > 0)
                fieldprops.put("size", "" + columnData.size);

            if (columnData.display != null && columnData.display.length() > 0)
                fieldprops.put("display", columnData.display);
            
            if (booleanFields.contains(columnData.name)) {
                fieldprops.put("isBoolean", "true");
            }
            else {
                fieldprops.put("isBoolean", "false");
            }
            
            if (radioFields.contains(columnData.name)) {
                fieldprops.put("isRadio", "true");
            }
            else {
                fieldprops.put("isRadio", "false");
            }
            
            
            if (checkboxFields.contains(columnData.name)) {
                fieldprops.put("isCheckbox", "true");
            }
            else {
                fieldprops.put("isCheckbox", "false");
            }
            if (hiddenFields.contains(columnData.name)) {
                fieldprops.put("isHidden", "true");
            }
            else {
                fieldprops.put("isHidden", "false");
            }
            if (requiredFields.contains(columnData.name)) {
                fieldprops.put("isRequired", "true");
            }
            else {
                fieldprops.put("isRequired", "false");
            }
            if (passwordFields.contains(columnData.name)) {
                fieldprops.put("isPassword", "true");
            }
            else {
                fieldprops.put("isPassword", "false");
            }
            if (textAreaFields.contains(columnData.name)) {
                fieldprops.put("size", getSize(sizeConfigs, columnData.name, "50"));
                fieldprops.put("isTextArea", "true");
            }
            else {
                fieldprops.put("isTextArea", "false");
            }
            if (wyswygTextAreaFields.contains(columnData.name)) {
                fieldprops.put("size", getSize(sizeConfigs, columnData.name, "50"));
                fieldprops.put("isWyswygTextArea", "true");
                fieldprops.put("isTextArea", "true"); // For the place where
                                                      // does not check wyswyg.
            }
            else {
                fieldprops.put("isWyswygTextArea", "false");
            }
            if (dropdownFields.contains(columnData.name)) {
                fieldprops.put("isDropDown", "true");
            }
            else {
                fieldprops.put("isDropDown", "false");
            }
            if (dropdownAutoLoadFields.contains(columnData.name)) {
                fieldprops.put("isDropDownAutoLoad", "true");
            }
            else {
                fieldprops.put("isDropDownAutoLoad", "false");
            }
            if (formIgnoreFields.contains(columnData.name)) {
                fieldprops.put("isFormIgnore", "true");
            }
            else {
                fieldprops.put("isFormIgnore", "false");
            }

            if (idFields.contains(columnData.name)) {
                fieldprops.put("isId", "true");
            }
            else {
                fieldprops.put("isId", "false");
            }
            if (dateFields.contains(columnData.name)) {
                fieldprops.put("isDate", "true");
            }
            else {
                fieldprops.put("isDate", "false");
            }
            if (immutableFields.contains(columnData.name)) {
                fieldprops.put("isImmutable", "true");
            }
            else {
                fieldprops.put("isImmutable", "false");
            }
            if (autosetDateFields.contains(columnData.name)) {
                fieldprops.put("isAutosetDate", "true");
            }
            else {
                fieldprops.put("isAutosetDate", "false");
            }
            if (autosetUpdateDateFields.contains(columnData.name)) {
                fieldprops.put("isAutosetUpdateDate", "true");
            }
            else {
                fieldprops.put("isAutosetUpdateDate", "false");
            }
            if (autosetCreateDateFields.contains(columnData.name)) {
                fieldprops.put("isAutosetCreateDate", "true");
            }
            else {
                fieldprops.put("isAutosetCreateDate", "false");
            }

            if (noPersistFields.contains(columnData.name)) {
                fieldprops.put("isNoPersist", "true");
            }
            else {
                fieldprops.put("isNoPersist", "false");
            }

            if (dsShortcutFields.contains(columnData.name)) {
                fieldprops.put("isDsShortField", "true");
            } else {
                fieldprops.put("isDsShortField", "false");
            }
            
            if (iosSkips.contains(columnData.name)) {
                fieldprops.put("isIOSSkip", "true");
            } else {
                fieldprops.put("isIOSSkip", "false");
            }
            
            if (ajaxFormFields.contains(columnData.name)) {
                fieldprops.put("isAjaxForm", "true");
            }
            else {
                // if ajaxFields are not specified in the list, add all fields
                // into the list
                if (ajaxFieldsSpecified)
                    fieldprops.put("isAjaxForm", "false");
                else
                    fieldprops.put("isAjaxForm", "true");
            }

            // ========== little bit more work
            if (!fieldprops.containsKey("size"))
                fieldprops.put("size", getSize(sizeConfigs, columnData.name, "70"));

            if (dropdownOptions.containsKey(columnData.name)) {
                List ddOptions = (List) dropdownOptions.get(columnData.name);
                fieldprops.put("dropdownOptions", ddOptions);
            }

            if (dropdownAutoLoadOptions.containsKey(columnData.name)) {
                Map ddOptions = (Map) dropdownAutoLoadOptions.get(columnData.name);
                fieldprops.put("dropdownAutoLoadOptions", ddOptions);
            }

            if (dropdownAutoLoadKey.containsKey(columnData.name)) {
                String val = (String) dropdownAutoLoadKey.get(columnData.name);
                fieldprops.put("dropdownAutoLoadKey", val);
            }

            if (defaultValues.containsKey(columnData.name)) {
                fieldprops.put("defaultValue", defaultValues.get(columnData.name));
            }
            if (dependencyIntegrityChecks.containsKey(columnData.name)) {
                fieldprops.put("dependencyIntegrityCheck", dependencyIntegrityChecks.get(columnData.name));
            }

            if (displayConfigs.containsKey(columnData.name)) {
                String displayConfig = (String) displayConfigs.get(columnData.name);

                if (displayConfig != null && displayConfig.trim().length() > 0) {
                    String defaultDisplay = (String) fieldprops.put("display", displayConfig);
                    m_logger.debug("Display string for field " + columnData.name + " changed from " + defaultDisplay + " to " + displayConfig);
                }
            }

            inputFields.add(fieldprops);
        }

        return inputFields;
    }

    public static Properties getSubProperties(Properties properties, String root) {
        return getSubProperties(properties, root, true);
    }

    public static Properties getSubProperties(Properties properties, String root, boolean includeDefaults) {
        Properties ret = new Properties();
        String rootStr = root + ".";
        String listStr = root + ".list.";
        String defaultListStr = "Default.list.";
        String defaultKeyStr = "Default.";

        String iosRoot = properties.getProperty(root + ".ios.root");
        if (iosRoot == null || iosRoot.isEmpty()) {
            iosRoot = root;
        }
        Enumeration enumerator = properties.propertyNames();

        while (enumerator.hasMoreElements()) {
            String key = (String) enumerator.nextElement();
            
            if ( key.indexOf("dropdownAutoLoadKey") >0){
            System.out.println("????????????? "+key);
            }
            if (key.indexOf("action_set_site_id") >= 0) {
            }

            if (includeDefaults && key.startsWith(defaultKeyStr)) {
                String itemKey = key.substring(defaultKeyStr.length());
                String val = properties.getProperty(key);
                val = replaceWithRootName(val, root, iosRoot);

                if (!ret.containsKey(itemKey)) {
                    ret.put(itemKey, val);
                    m_logger.debug("**************** " + itemKey + "----->" + val);
                }
            }

            if (key.startsWith(listStr)) {
                String listKey = key.substring(listStr.length());
                String list = properties.getProperty(key);

                List listVal = getList(list);

                ret.put(listKey, listVal);
                ret.put("list." + listKey, list);
                m_logger.debug("****************>>>>>>>>>>>>>>>>>> " + listKey  + "----->" + listVal);
                continue;
            }

            if (key.startsWith(defaultListStr)) {
                String listKey = key.substring(defaultListStr.length());
                String list = properties.getProperty(key);

                List listVal = getList(list);

                if ( !ret.containsKey(listKey))
                    ret.put(listKey, listVal);
                
                if ( !ret.containsKey("list." + listKey))
                    ret.put("list." + listKey, list);
                m_logger.debug("****************>>>>>>>>>>>>>>>>>> " + listKey  + "----->" + listVal);
                continue;
            }
            
            
            if (key.startsWith(rootStr)) {
                String newKey = key.substring(rootStr.length());
                String val = properties.getProperty(key);
                val = replaceWithRootName(val, root, iosRoot);
                String replaced = (String) ret.put(newKey, val);
                if (replaced != null) {
                    m_logger.debug("****************XX rePLACED " + newKey + "----->" + replaced);
                }
            }

            // Special top level custom list
            // in the config file, custom list can be defined. as following
            // TEMPLATEAPP.customlist.ds_extent_methods=getData%boolean%return
            // true:arg%String/arg2%int,getTimestamp%void
            // Unlike other list configs, this is not related to input fields.
            // Created to provide any content that can be defined in a list.
            // getMapList() method does all things needed to this.
            // the second part of the config keys become the definition key used
            // in template.

            String customListStr = root + ".customlist.";
            String extendMethodKey = "ds_extent_methods";
            String extraFieldsKey = "extra_fields";
            String extraCommands = "extra_commands";
            String extraSubCommands = "extra_sub_commands";
            String confirmToCommands = "confirm_to_commands";
            String extraFormButtons = "form_buttons";
            //String dsShortcutFields = "ds_shortcut_fields";

            // Format methodName%method return type%default method body:arg1
            // name%arg1 Type/arg2 name%Arg2 type/..., ...
            if (key.startsWith(customListStr + extendMethodKey)) {

                String list = properties.getProperty(key);
                List topCustomList = getMapList(list, new ListToMethodParameter());

                ret.put(extendMethodKey, topCustomList);

                m_logger.debug("## CustomList " + extendMethodKey + "=" + topCustomList);
            }

            if (key.startsWith(customListStr + extraFieldsKey)) {

                String list = properties.getProperty(key);
                List topCustomList = getMapList(list, new SimpleSingleValueSetter());

                ret.put(extraFieldsKey, topCustomList);

                m_logger.debug("## CustomList " + extraFieldsKey + "=" + topCustomList);
            }
            
            if (key.startsWith(customListStr + extraCommands)) {
                String list = properties.getProperty(key);
                List topCustomList = getList(list);

                ret.put(extraCommands, topCustomList);

                m_logger.debug("## CustomList " + extraCommands + "=" + topCustomList);
            }
            
            if (key.startsWith(customListStr + extraSubCommands)) {
                String list = properties.getProperty(key);
                List topCustomList = getList(list);

                ret.put(extraSubCommands, topCustomList);

                m_logger.debug("## CustomList " + extraSubCommands + "=" + topCustomList);
            }
            
            if (key.startsWith(customListStr + confirmToCommands)) {
                String list = properties.getProperty(key);
                List customList = getList(list);

                ret.put(confirmToCommands, customList);

                m_logger.debug("## CustomList " + confirmToCommands + "=" + customList);
            }
            
            if (key.startsWith(customListStr + extraFormButtons)) {
                String list = properties.getProperty(key);
                List customList = getList(list);

                ret.put(extraFormButtons, customList);

                m_logger.debug("## CustomList " + confirmToCommands + "=" + customList);
            }

        }

        return ret;
    }

    // Dup keys are removed from target if it exists in main
    public void removeDupsFrom(Map main, Map target){

        Set existingKeys = new HashSet();
        for (Iterator iterator = target.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if ( main.containsKey(key))
                existingKeys.add(key);
        }

        
        for (Iterator iterator = existingKeys.iterator(); iterator.hasNext();) {
            String  key = (String ) iterator.next();
            target.remove(key);
        }
        
    }
    
    
    public static void makeSurePropKey(Properties prop, String key, String defaultVal){
        if (!prop.containsKey(key)) prop.put(key, defaultVal);
    }

    public static void makeSurePropKeyExists(Properties prop, String key){
        if (!prop.containsKey(key)) throw new RuntimeException("###Need key " + key);
    }

    
    public static void makeSureNoPropKey(Properties prop, String key){
        if (prop.containsKey(key)) prop.remove(key);
    }
    
    public static void checkProperties(String appName, Properties prop) {

        makeSurePropKey(prop, "ds_key", "SiteId");
        //makeSurePropKey(prop, "jspFormDir", "form");
        makeSurePropKey(prop, "useDataHolder", "false");
        makeSurePropKey(prop, "persistEnable", "true");
        
        makeSurePropKeyExists(prop, "action_set_site_id");
        makeSurePropKey(prop, "action_set_site_id", "true");
        makeSurePropKey(prop, "action_set_user_id", "false");
        makeSurePropKey(prop, "action_check_user_id_on_update", "false");
        makeSurePropKey(prop, "actionLoginRequired", "true");
        makeSurePropKey(prop, "actionSiteAdminOnly", "true");
        makeSurePropKey(prop, "actionSuerAdminOnly", "false");
        
        for (Enumeration enumer = prop.keys(); enumer.hasMoreElements();) {
            String key = (String) enumer.nextElement();

            if (key.startsWith("list."))
                continue;

            Object obj = prop.getProperty(key);

            if (key.equals("classSuffix"))
                continue;

            if (obj instanceof String) {
                String value = (String) obj;
                if (value == null || value.trim().equals("")) {
                    prop.remove(key);
                }
            }
        }
        
        for (Enumeration enumer = prop.keys(); enumer.hasMoreElements();) {
            String key = (String) enumer.nextElement();
            Object obj = prop.getProperty(key);
            m_logger.debug("        " + key + ":" + obj);
        }        
    }

    protected List getDataFrom(Properties props) {

        List ret = new ArrayList();

        String fieldList = (String) props.get("fieldList");
        if (fieldList == null)
            return ret;

        StringTokenizer tokenizer = new StringTokenizer(fieldList, ", ");

        while (tokenizer.hasMoreTokens()) {
            String field = tokenizer.nextToken();

            ColumnData data = new ColumnData();

            data.name = (String) props.getProperty(field + ".name", field);
            data.display = (String) props.getProperty(field + ".display", convertDbFieldToJava(field, true, true));
            data.typeName = (String) props.getProperty(field + ".typeName", "VARCHAR");
            data.size = props.getProperty(field + ".size") == null ? 30 : Integer.parseInt(props.getProperty(field + ".size"));

            if (data.name != null && data.name.length() > 0) {
                ret.add(data);
            }
        }

        return ret;
    }

    //================================================================================================================
    //
    //================================================================================================================
    
    // Simple comma list to list of String token
    // INPUT  should be property_key:val1,val2,val3,val4
    // OUTPUT should be the list of String tokens
    
    public static ArrayList getList(String list) {
        StringTokenizer tokenizer = new StringTokenizer(list, ", ");

        ArrayList ret = new ArrayList();

        while (tokenizer.hasMoreTokens()) {
            ret.add(tokenizer.nextToken());
        }

        return ret;
    }

    // Simple comma list to list of String token
    // INPUT  should be property_key:val1,val2,val3,val4
    // OUTPUT should be the set of String tokens

    public static Set getSet(String list) {
        Set ret = new HashSet();

        if (list == null)
            return ret;

        StringTokenizer tokenizer = new StringTokenizer(list, ", ");

        while (tokenizer.hasMoreTokens()) {
            ret.add(tokenizer.nextToken());
        }
        return ret;
    }

    
    
    static int GETMAP_TYPE_SIMPLE           = 1;
    static int GETMAP_TYPE_VALUE_AS_MAPMAP  = 2;
    static int GETMAP_TYPE_VALUE_AS_LIST    = 3;
    static int GETMAP_TYPE_FOR_AUTOLOAD_OPTION  = 4;
    
    public static Map getMap(String list) {
        return getMap(list, GETMAP_TYPE_SIMPLE);
    }

    // Input string format For Simple Type - m1:v1,m2:v2,m3:v3,....
    // For MapMap           - m1:v1k1%v1v1/v1k2%v1v2/v1k3%v1v3/v1k4%v1v4, 
    //          OUTPUT      - MAP(Key - m1 m2 ) - MAP ( Key - k1 k2 k3 )
    // For ValueList        - m1:internalValue1%displayString1/internalValue2%displayString2/internalValue3%displayString3,m2:internalValue1%displayString1/internalValue2%displayString2/internalValue3%displayString3
    //          OUTPUT      - MAP(Key - m1 m2 ) - LIST ("/") - MAP ( Key - "internalValue" "displayString")
    // For AutoLoadOption   - m1:v1-1/v1-2,m2:v2-1/v2-2,m3:v3-1/v3-2 - There would only two value list e.g. no v1-3.
    //          OUTPUT      - MAP(Key - m1 m2 ) - MAP ( Key - "dependObject" "dependMethod")
    // For Simple KVP       - m1:v1,m2:v2
    //          OUTPUT      - MAP(Key - m1 m2 )
    
    public static Map getMap(String list, int type) {
        Map ret = new HashMap();

        if (list == null)
            return ret;

        StringTokenizer tokenizer = new StringTokenizer(list, ",");
        
        while (tokenizer.hasMoreTokens()) {
            String val = tokenizer.nextToken();
            int pos = val.indexOf(":");
            if (pos < 0)
                continue;

            String name = val.substring(0, pos).trim();
            String value = val.substring(pos + 1).trim();

            if (type == GETMAP_TYPE_VALUE_AS_LIST ) {
                List valueList = new ArrayList();
                StringTokenizer tokenizer2 = new StringTokenizer(value, "/");
                while (tokenizer2.hasMoreTokens()) {
                    String listEntry = tokenizer2.nextToken();

                    String kvm[] = listEntry.split("%");
                    Map map = new HashMap();
                    String internalValue = "";
                    String displayString = "";
                    if (kvm.length == 1) {
                        internalValue = kvm[0];
                        displayString = kvm[0];
                    }
                    else {
                        internalValue = kvm[0];
                        displayString = kvm[1];
                    }
                    map.put("internalValue", internalValue);
                    map.put("displayString", displayString);

                    valueList.add(map);
                }
                ret.put(name, valueList);
            } else if (type == GETMAP_TYPE_VALUE_AS_MAPMAP ) {
                StringTokenizer tokenizer2 = new StringTokenizer(value, "/");
                Map map = new HashMap();
                while (tokenizer2.hasMoreTokens()) {
                    String listEntry = tokenizer2.nextToken();

                    String kvm[] = listEntry.split("%");
                    String subName = "";
                    String subValue = "";
                    if (kvm.length == 1) {
                        subName = kvm[0];
                        subValue = kvm[0];
                    }
                    else {
                        subName = kvm[0];
                        subValue = kvm[1];
                    }
                    map.put(subName, subValue);
                }
                ret.put(name, map);
            }
            else if (type == GETMAP_TYPE_FOR_AUTOLOAD_OPTION ) {
                // SAMPLE
                // list_subject_style_id:StyleConfig/StyleKey,list_data_style_id:StyleConfig/StyleKey
                Map valueMap = new HashMap();
                StringTokenizer tokenizer2 = new StringTokenizer(value, "/");

                String objectName = tokenizer2.nextToken();
                valueMap.put("dependObject", objectName);

                String functionName = tokenizer2.nextToken();
                valueMap.put("dependMethod", functionName);

                ret.put(name, valueMap);

            }
            else { //GETMAP_TYPE_SIMPLE
                ret.put(name, value);

            }
        }

        return ret;
    }

    // INPUT m1%EXTENT_1%EXTENT_2:mvInt1%mvDisp1/mvInt2%mvDisp2/mvInt3%mvDisp3,          m2%EXTENT_1%EXTENT_2:mvInt1%mvDisp1/mvInt2%mvDisp2/mvInt3%mvDisp3
    // OUTPUT STRUCURE would be
    // List (",") - MAP ( key "NAME" "NAME_EXTENT_n" "FORMATTED_VALUES" "VALUES") 
    //                                              - VALUES LIST("/") MAP ("internalValue" "displayString")
    //                                              - FORMATTED_VALUES LIST("/")  - Data is formated by ListFormatter. 
    public static List getMapList(String list, ListFormatter listFormatter) {

        List ret = new ArrayList();

        if (list == null)
            return ret;

        StringTokenizer tokenizer = new StringTokenizer(list, ",");
        while (tokenizer.hasMoreTokens()) {
            String val = tokenizer.nextToken();

            Map m = new HashMap();

            int pos = val.indexOf(":");

            String name = (pos < 0 ? val : val.substring(0, pos).trim());
            String nameSeparate[] = name.split("%");

            m.put("NAME", nameSeparate[0]);

            for (int i = 1; i < 5; i++) {
                if (i < nameSeparate.length)
                    m.put("NAME_EXTENT_" + i, nameSeparate[i]);
                else
                    m.put("NAME_EXTENT_" + i, "");
            }

            if (pos < 0) {
                m.put("FORMATTED_VALUES", "");
                ret.add(m);
                continue;
            }

            String value = val.substring(pos + 1).trim();

            List valueList = new ArrayList();
            StringTokenizer tokenizer2 = new StringTokenizer(value, "/");
            while (tokenizer2.hasMoreTokens()) {
                String listEntry = tokenizer2.nextToken();

                String kvm[] = listEntry.split("%");
                Map map = new HashMap();
                String internalValue = "";
                String displayString = "";
                if (kvm.length == 1) {
                    internalValue = kvm[0];
                    displayString = kvm[0];
                }
                else {
                    internalValue = kvm[0];
                    displayString = kvm[1];
                }
                map.put("internalValue", internalValue);
                map.put("displayString", displayString);

                valueList.add(map);
            }

            // Generalized form. It is in the form of list of parameterized
            // values. list of maps. map has two keys whichare internalValue
            // (left of %) displayString ( right of %) This is universal rule.
            m.put("VALUES", valueList);

            // List of arg can be formated into different form. Is stored in
            // FORMATED_VALUES.
            if (listFormatter != null)
                m.put("FORMATTED_VALUES", listFormatter.format(value, "/"));
            else
                m.put("FORMATTED_VALUES", "");

            ret.add(m);
        }
        return ret;
    }
    
    //================================================================================================================
    //================================================================================================================
    //================================================================================================================
    
    public static String getSize(Map map, String name, String defaultVal) {

        if (map.containsKey(name))
            return (String) map.get(name);

        return defaultVal;
    }

    public static String convertDbFieldToJava(String dbField) {
        return convertDbFieldToJava(dbField, true, false);
    }

    public static String convertDbFieldToJava(String dbField, boolean makeFirstLetterUpper) {
        return convertDbFieldToJava(dbField, makeFirstLetterUpper, false);
    }

    public static String convertDbFieldToJava(String dbField, boolean makeFirstLetterUpper, boolean replaceUnderscore) {

        StringBuffer ret = new StringBuffer();
        boolean covertThis = makeFirstLetterUpper;

        for (int i = 0; i < dbField.length(); i++) {

            String single = dbField.charAt(i) + "";

            if (single.equals("_")) {
                covertThis = true;

                if (replaceUnderscore)
                    ret.append(" ");
                continue;
            }

            if (covertThis) {
                single = single.toUpperCase();
                covertThis = false;
            }
            // else
            // single = single.toLowerCase();

            ret.append(single);
        }

        return ret.toString();
    }

    public static String toFirstCharLower(String string) {
        if (string == null || string.length() == 0)
            return string;

        if (string.length() == 1)
            return string.toLowerCase();
        String first = string.substring(0, 1);
        String second = string.substring(1);

        return first.toLowerCase() + second;

    }

    public static String toUnderscoreString(String string) {
        if (string == null || string.length() == 0)
            return string;

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);

            if (Character.isUpperCase(ch)) {
                if (i > 0)
                    buf.append("_");
                buf.append(Character.toLowerCase(ch));
            }
            else {
                buf.append(Character.toLowerCase(ch));
            }
        }
        return buf.toString();
    }

    // RootModeulName -> root-module-name
    public static String tooRootDash(String string) {
        if (string == null || string.length() == 0)
            return string;

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);

            if (Character.isUpperCase(ch)) {
                if (i > 0)
                    buf.append("-");
                buf.append(Character.toLowerCase(ch));
            }
            else {
                buf.append(Character.toLowerCase(ch));
            }
        }
        return buf.toString();
    }

    private boolean isNull(String val) {

        if (val == null || val.trim().equals(""))
            return true;
        return false;
    }

    private static String replaceWithRootName(String rowValue, String root, String iosRoot) {

        String ret = rowValue.replace("${root}", root);
        ret = ret.replace("${rootLow}", toFirstCharLower(root));
        ret = ret.replace("${rootUnderscore}", toUnderscoreString(root));
        ret = ret.replace("${rootDash}", tooRootDash(root));

        if (iosRoot != null && !iosRoot.isEmpty()) {
            ret = ret.replace("${iosRoot}", iosRoot);
            ret = ret.replace("${iosRootLow}", toFirstCharLower(iosRoot));
        }
        
        return ret;
    }

    private void printProperties(Properties props){
        
        Set names = new TreeSet();
        names.addAll(props.keySet());
        
        
        for (Iterator iterator = names.iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            
            m_logger.info( StringUtils.rightPad(name, 50) + ": " + props.getProperty(name));
            
        }
    }
    

    private void printPropertiesInFormat(List props){

        m_logger.debug("***************************************************************************************************");
        m_logger.debug("***************************************************************************************************");

        for (Iterator iterator = props.iterator(); iterator.hasNext();) {
            Map m = (Map) iterator.next();
            
            String name = (String) m.get("name");

            Map sorted = new TreeMap(m);
            
            System.out.println(name + "  ---------------------------------------------------------------------------------------------");
            for (Iterator iterator2 = sorted.keySet().iterator(); iterator2.hasNext();) {
                String key = (String) iterator2.next();
                
                System.out.println("             "  + StringUtils.rightPad(key, 40) + ": " + sorted.get(key));
                
            }
            
        }
        
        m_logger.debug("***************************************************************************************************");
        m_logger.debug("***************************************************************************************************");
        
    }
    
    public static void main(String[] args) {

        OUter.Inn i = new OUter.Inn();
        
        i.doThis();
        
        
        if (args.length == 2)
            new AutoGen(args[0]);
        else
            new AutoGen();
    }

    private static Logger m_logger = LoggerFactory.getLogger(AutoGen.class);
}



// expect one value in the list of values of value part of a list and put into FORMATTED. 
class SimpleSingleValueSetter implements ListFormatter {
    public String format(String list) {
        return format(list, "/");
    }

    public String format(String list, String separater) {
        return list;
    }    
}
class ListToMethodParameter implements ListFormatter {

    public String format(String list) {
        return format(list, "/");
    }

    public String format(String list, String separater) {

        StringBuilder sb = new StringBuilder();
        StringTokenizer tokenizer2 = new StringTokenizer(list, separater);
        boolean firstProcessed = false;
        while (tokenizer2.hasMoreTokens()) {
            String listEntry = tokenizer2.nextToken();

            String kvm[] = listEntry.split("%");
            String internalValue = "";
            String displayString = "";
            if (kvm.length == 1) {
                internalValue = kvm[0];
                displayString = kvm[0];
            }
            else {
                internalValue = kvm[0];
                displayString = kvm[1];
            }

            if (firstProcessed) {
                sb.append(", ");
            }
            sb.append(displayString).append(" ").append(internalValue);
            firstProcessed = true;
        }

        return sb.toString();
    }

}
