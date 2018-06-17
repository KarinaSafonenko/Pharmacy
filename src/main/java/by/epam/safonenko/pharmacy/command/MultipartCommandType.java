package by.epam.safonenko.pharmacy.command;


import by.epam.safonenko.pharmacy.command.impl.AddProduct;

public enum  MultipartCommandType {
    ADD_PRODUCT(new AddProduct());

    private MultipartCommand command;

    MultipartCommandType(MultipartCommand command){
        this.command = command;
    }

    public MultipartCommand getCommand() {

        return command;
    }
}
