package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductAPI {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductAPI(JdbcTemplate jdbcTemplateAutoWired) {
        jdbcTemplate = jdbcTemplateAutoWired;
    }

    @RequestMapping("add")
    private List<Product> add(@ModelAttribute Product product) {
        String sql = "INSERT INTO product (name,description,availability,price) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getAvailability(), product.getPrice());
        return list();
    }

    @RequestMapping("list")
    private List<Product> list() {
        List<Product> products;
        products = jdbcTemplate.query("select * from product", (rs, rowId) -> {
            return new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getLong("availability"),
                    rs.getLong("price")
            );
        });
        return products;
    }
}
