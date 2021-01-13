package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite extends TestCase {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testBoardsListToBoardsDtoListMapper() {
        //Given
        List<TrelloList> testTrelloList1 = new ArrayList<>();
        testTrelloList1.add(new TrelloList("test1", "test1", false));
        testTrelloList1.add(new TrelloList("test2", "test2", true));
        testTrelloList1.add(new TrelloList("test3", "test3", false));
        TrelloBoard trelloBoard1 = new TrelloBoard("testBoard_id1", "testBoard_name1", testTrelloList1);

        List<TrelloList> testTrelloList2 = new ArrayList<>();
        testTrelloList2.add(new TrelloList("test4", "test4", false));
        testTrelloList2.add(new TrelloList("test5", "test5", true));
        testTrelloList2.add(new TrelloList("test6", "test6", false));
        TrelloBoard trelloBoard2 = new TrelloBoard("testBoard_id2", "testBoard_name2", testTrelloList2);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> resultDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(2, resultDtos.size());
        assertEquals("testBoard_id1", resultDtos.get(0).getId());
        assertEquals("testBoard_name1", resultDtos.get(0).getName());
        assertEquals(3, resultDtos.get(0).getLists().size());
    }

    @Test
    public void testBoardDtoListToBoardListMapper() {
        //Given
        List<TrelloListDto> list1 = new ArrayList<>();
        list1.add(new TrelloListDto("id1", "name1", false));
        list1.add(new TrelloListDto("id2", "name2", false));
        list1.add(new TrelloListDto("id3", "name3", true));
        TrelloBoardDto dto1 = new TrelloBoardDto("testDto_id1", "testDto_name1", list1);

        List<TrelloListDto> list2 = new ArrayList<>();
        list2.add(new TrelloListDto("id4", "name4", false));
        list2.add(new TrelloListDto("id5", "name5", false));
        list2.add(new TrelloListDto("id6", "name6", true));
        TrelloBoardDto dto2 = new TrelloBoardDto("testDto_id2", "testDto_name2", list2);

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(dto1);
        trelloBoardDtoList.add(dto2);

        //When
        List<TrelloBoard> resultList = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals(2, resultList.size());
        assertEquals("testDto_id1", resultList.get(0).getId());
        assertEquals("testDto_name1", resultList.get(0).getName());
        assertEquals(3, resultList.get(0).getLists().size());
    }

    @Test
    public void testTrelloListsToTrelloListsDtoMapper() {
        //Given
        List<TrelloList> testTrelloLists = new ArrayList<>();
        testTrelloLists.add(new TrelloList("test_id1", "test_name1", false));
        testTrelloLists.add(new TrelloList("test_id2", "test_name2", true));
        testTrelloLists.add(new TrelloList("test_id3", "test_name3", false));

        //When
        List<TrelloListDto> resultList = trelloMapper.mapToListDto(testTrelloLists);

        //Then
        assertEquals(3, resultList.size());
        assertEquals("test_id1", resultList.get(0).getId());
        assertEquals("test_name1", resultList.get(0).getName());
        assertFalse(resultList.get(0).isClosed());
    }

    @Test
    public void testTrelloListsDtoToTrelloListsMapper() {
        //Given
        List<TrelloListDto> testTrelloListDtos = new ArrayList<>();
        testTrelloListDtos.add(new TrelloListDto("test_id1", "test_name1", false));
        testTrelloListDtos.add(new TrelloListDto("test_id2", "test_name2", false));
        testTrelloListDtos.add(new TrelloListDto("test_id3", "test_name3", true));

        //When
        List<TrelloList> resultList = trelloMapper.mapToList(testTrelloListDtos);

        //Then
        assertEquals(3, resultList.size());
        assertEquals("test_id1", resultList.get(0).getId());
        assertEquals("test_name1", resultList.get(0).getName());
        assertFalse(resultList.get(0).isClosed());
    }

    @Test
    public void testCardToCardDtoMapper() {
        //Given
        TrelloCard testCard = new TrelloCard("test_name", "test_description", "top", "list_id");

        //When
        TrelloCardDto resultCard = trelloMapper.mapToCardDto(testCard);

        //Then
        assertEquals("test_name", resultCard.getName());
        assertEquals("test_description", resultCard.getDescription());
        assertEquals("top", resultCard.getPos());
        assertEquals("list_id", resultCard.getListId());
    }

    @Test
    public void testCardDtoToCardMapper() {
        //Given
        TrelloCardDto testCardDto = new TrelloCardDto("test_name", "test_description", "top", "list_id");

        //When
        TrelloCard resultCard = trelloMapper.mapToCard(testCardDto);

        //Then
        assertEquals("test_name", resultCard.getName());
        assertEquals("test_description", resultCard.getDescription());
        assertEquals("top", resultCard.getPos());
        assertEquals("list_id", resultCard.getListId());
    }
}