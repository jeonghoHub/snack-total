package jpabook.jpashop;

import jpabook.jpashop.controller.Message;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.VoteRepository;
import jpabook.jpashop.snackDomain.SnackItem;
import static org.assertj.core.api.Assertions.assertThat;

import jpabook.jpashop.snackDomain.voteRankingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@SpringBootTest
class JpashopApplicationTests {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private VoteRepository voteRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void 스낵아이템_중복체크_이미있는간식은_실패떠야함() {
		//give
		Message message = new Message();
		String itemName = "마가렛트"; //이미 존재하는 아이템

		//when
		try	{
			SnackItem duplication = itemRepository.duplication(itemName);
			message.setData("fail");
		} catch (EmptyResultDataAccessException e) {
			message.setData("success");
		}
		//then
		assertThat(message.getData()).isEqualTo("fail");
	}
	@Test
	void 이번달_간식_투표_순위() {
		List<voteRankingDto> query = voteRepository.thisMonthSnackRanking();
		System.out.println(query);
	}
}
