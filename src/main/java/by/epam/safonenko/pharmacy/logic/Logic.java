package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.exception.LogicException;

import java.util.Date;
import java.util.Calendar;
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

    default int countLeftBorder(int infoListSize, int page) {
        int leftBorder = FIRST_PAGE_NUMBER + (page - FIRST_PAGE_NUMBER) * MAX_NUMBER_ON_PAGE;
        if (leftBorder > infoListSize){
            leftBorder = infoListSize;
        }
        return leftBorder;
    }

    default int countRightBorder(int infoListSize, int page) throws LogicException {
        int rightBorder = page * MAX_NUMBER_ON_PAGE;
        if (rightBorder > infoListSize){
            rightBorder = infoListSize;
        }
        int leftBorder = countLeftBorder(infoListSize, page);
        if (rightBorder < leftBorder){
            throw new LogicException("The list contains fewer elements. Can't count right border.");
        }
        return rightBorder;
    }

   default List formSubList(List infoList, int page) throws LogicException {
        int size = infoList.size();
        int leftBorder = countLeftBorder(size, page);
        int rightBorder = countRightBorder(size, page);
        return infoList.subList(leftBorder - FIRST_PAGE_NUMBER, rightBorder);
   }

   default Date getCurrentDate(){
       Calendar today = Calendar.getInstance();
       today.set(Calendar.HOUR,0);
       today.set(Calendar.HOUR_OF_DAY,0);
       today.set(Calendar.MINUTE,0);
       today.set(Calendar.SECOND,0);
       today.set(Calendar.MILLISECOND,0);
       return today.getTime();
   }

   default String formAddress(String street, String house, String flat){
       return (street + " " + house + "-" + flat);
   }

}
