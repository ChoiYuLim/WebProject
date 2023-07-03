package vo;

public class AccountDTO {
    private String id;
    private int number;

    public AccountDTO(String id, int number) {
        super();
        this.id = id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
