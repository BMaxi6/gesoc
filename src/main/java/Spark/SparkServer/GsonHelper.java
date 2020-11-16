package Spark.SparkServer;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.google.gson.Gson;

import java.io.IOException;

public class GsonHelper implements Helper<Object> {
    @Override
    public CharSequence apply(Object o, Options options) throws IOException {
        Gson gson= new Gson();
        return gson.toJson(o);
    }
}
