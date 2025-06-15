import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        excludeName = {
                "org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration"
        }
)
public class StudyProjectApplication {
    public static void main(String[] args) {

        SpringApplication.run(StudyProjectApplication.class, args);
    }
}
