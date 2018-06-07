package by.epam.safonenko.pharmacy.logic;

import java.util.List;

public interface Logic {
    int MAX_NUMBER_ON_PAGE = 12;

    default int countPageNumber(List infoList){
        return (int) Math.ceil(infoList.size()/MAX_NUMBER_ON_PAGE);
    }
}
