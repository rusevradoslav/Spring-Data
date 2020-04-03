package entities.labels;

import entities.shampoos.BasicShampoo;

import javax.persistence.*;

@Entity
public class BasicLabel implements Label {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String subTitle;

    protected BasicLabel() {
    }

    @OneToOne(targetEntity = BasicShampoo.class, mappedBy = "label", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BasicShampoo shampoo;

    public BasicLabel(String title, String subtTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subtitle) {
        this.subTitle = subTitle;
    }

}
