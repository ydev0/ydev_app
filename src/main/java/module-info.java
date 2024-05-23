module ydev.app {
    exports com.ydev00.model.thread;
    exports com.ydev00.model.user;
    exports com.ydev00.model.image;
    exports com.ydev00.util;
    requires com.google.gson;
    requires java.sql;
    requires jetty.http;
    requires spark.core;
    requires java.sql.rowset;
}