package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.exception.LogicException;

import java.util.List;

public interface Logic {
    int MAX_NUMBER_ON_PAGE = 12;
    int POPULAR_PRODUCT_NUMBER = 3;
    int FIRST_PAGE_NUMBER = 1;

    default int countPageNumber(List infoList){
        return (int) Math.ceil((double) infoList.size()/MAX_NUMBER_ON_PAGE);
    }

    default int getMaxNumberOnPage(){
        return MAX_NUMBER_ON_PAGE;
    }

    default int getPopularProductNumber(){
        return POPULAR_PRODUCT_NUMBER;
    }

    default int getFirstPageNumber(){
        return FIRST_PAGE_NUMBER;
    }

    default int countLeftBorder(List infoList, int page) throws LogicException {
        int leftBorder = FIRST_PAGE_NUMBER + (page - FIRST_PAGE_NUMBER) * MAX_NUMBER_ON_PAGE;
        if (leftBorder > infoList.size()){
            throw new LogicException("Incorrect page number. The list contains fewer elements.");
        }
        return leftBorder;
    }

    default int countRightBorder(List infoList, int page) throws LogicException {
        int rightBorder = page * MAX_NUMBER_ON_PAGE;
        if (rightBorder > infoList.size()){
            rightBorder = infoList.size();
        }
        int leftBorder = countLeftBorder(infoList, page);
        if (rightBorder < leftBorder){
            throw new LogicException("The list contains fewer elements. Can't count right border.");
        }
        return rightBorder;
    }

   default List formSubList(List infoList, int page) throws LogicException {
        int leftBorder = countLeftBorder(infoList, page);
        int rightBorder = countRightBorder(infoList, page);
        return infoList.subList(leftBorder - FIRST_PAGE_NUMBER, rightBorder);
   }

}
