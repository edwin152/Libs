package com.edwin.database;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Schema;

public class GreenDao {

    private static final String PROJECT_PATH = "E:/project/Libs/database/src/main/java";
    private static final String PACKAGE_NAME = GreenDao.class.getPackage().getName();

    public static void main(String[] arg) throws Exception {
        Schema schema = new Schema(0, PACKAGE_NAME + ".bean");
        schema.setDefaultJavaPackageDao(PACKAGE_NAME + ".dao");

        addNode(schema);
        new DaoGenerator().generateAll(schema, PROJECT_PATH);
    }

    /**
     * Define tables.
     * Define columns for each table.
     *
     * @param schema Database schema.
     */
    private static void addNode(Schema schema) {
//        Entity user = schema.addEntity("User");
//        user.addStringProperty("name");
    }


}
