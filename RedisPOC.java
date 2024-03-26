import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@RestController
public class RedisDemoApplication {

    private final Jedis jedis;

    public RedisDemoApplication() {
        jedis = new Jedis("your-redis-host.redis.cache.windows.net", 6379);
        jedis.auth("your-redis-access-key");
    }

    // Route to set a value in Redis
    @PostMapping("/set")
    public String setValue(@RequestBody KeyValue keyValue) {
        try {
            jedis.set(keyValue.getKey(), keyValue.getValue());
            System.out.println("Value '" + keyValue.getValue() + "' set for key '" + keyValue.getKey() + "' in Redis");
            return "Value set successfully in Redis";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error setting value in Redis";
        }
    }

    // Route to get a value from Redis
    @GetMapping("/get/{key}")
    public String getValue(@PathVariable String key) {
        try {
            String result = jedis.get(key);
            System.out.println("Value for key '" + key + "' in Redis: " + result);
            return "Value for key '" + key + "' in Redis: " + result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getting value from Redis";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    static class KeyValue {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
