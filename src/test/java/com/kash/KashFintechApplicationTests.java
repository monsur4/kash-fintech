package com.kash;

import com.kash.dto.UserDto;
import com.kash.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
class KashFintechApplicationTests {

	@Test
	void BeanUtilCopyTest() {
		// given
		UserDto userDto = new UserDto("Mons", "Ok", 43, "ok.email", "98ruir");
		User user = new User("1", "Mons", "Ok", 3, "ok.email", "98ruir");

		// when
		BeanUtils.copyProperties(userDto, user);

		// then
		assertThat("1").isEqualTo(user.getId());
	}

}
