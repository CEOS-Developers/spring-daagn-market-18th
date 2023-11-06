package com.ceos18.market;

import com.ceos18.market.domain.product.ProductRepository;
import com.ceos18.market.domain.UserRepository;
import com.ceos18.market.database.Product;
import com.ceos18.market.database.User;
import com.ceos18.market.database.enums.StatusCode;
import com.ceos18.market.database.enums.TradingCode;
import com.ceos18.market.database.enums.TradingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("User DB에 잘 저장이 되는지 확인")
    void saveProduct() {
        User user = User.builder()
                .userNo("test")
                .nickName("test")
                .phoneNumber("01012345678")
                .userImageUrl("test")
                .address("test")
                .userAgent("test")
                .privacyYN(StatusCode.Y)
                .mannerTemperature("36.5")
                .addressCertificationYN(StatusCode.N)
                .marketingYN(StatusCode.N)
                .build();

        userRepository.save(user);

        Product product = Product.builder()
                .userNo(user)
                .title("test")
                .keyword("test")
                .content("test")
                .tradingCode(TradingCode.SELL)
                .tradingOptionCode(StatusCode.N)
                .price(12300)
                .tradingStatus(TradingStatus.COMPLETED)
                .build();
        productRepository.save(product);

        assertThat(productRepository.findAll().size()).isEqualTo(1);
        assertThat(productRepository.findById(1L).get().getUserNo()).isEqualTo(user);
    }
}
