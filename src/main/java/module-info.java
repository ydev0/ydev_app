module ydev.app {
    exports com.ydev00.model.thread;
    exports com.ydev00.model.user;
    exports com.ydev00.model.image;
    exports com.ydev00.util;
    exports com.ydev00.dao;

    opens com.ydev00.model.user to com.google.gson;
    opens com.ydev00.model.image to com.google.gson;
    opens com.ydev00.model.thread to com.google.gson;
    opens com.ydev00.util to com.google.gson;

    requires com.google.gson;
    requires java.sql;
    requires jetty.http;
    requires spark.core;
    requires java.sql.rowset;
}
