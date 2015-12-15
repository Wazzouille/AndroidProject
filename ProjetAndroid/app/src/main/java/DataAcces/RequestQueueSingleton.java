package DataAcces;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.etu26132.e_night.MonApp;

/**
 * Created by Degraux on 11-12-15.
 */
public class RequestQueueSingleton {

        private static RequestQueueSingleton instance = null;
        private RequestQueue mRequestQueue;

        private RequestQueueSingleton(){
            mRequestQueue = Volley.newRequestQueue(MonApp.getInstance());
        }

        public static RequestQueueSingleton getInstance() {
            if(instance==null) {
                instance = new RequestQueueSingleton();
            }

            return instance;
        }

        public RequestQueue getRequestQueue(){
            return mRequestQueue;
        }
}
