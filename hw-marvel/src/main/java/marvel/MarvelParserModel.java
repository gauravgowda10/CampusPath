package marvel;

import com.opencsv.bean.CsvBindByName;

public class MarvelParserModel {
    /**
     * name of marvel universe character
     */
    @CsvBindByName
    private String hero;

    /**
     * the book that marvel character appeared in
     */
    @CsvBindByName
    private String book;


    /**
     * get the city of the sighting
     *
     * @return city of the sighting
     */
    public String getHero() {
        return hero;
    }

    /**
     * set the name of the character
     *
     * @param hero of character in the marvel universe
     */
    public void setHero(String hero) {
        this.hero = hero;
    }

    /**
     * get the state of the sighting
     *
     * @return state of the sighting
     */
    public String getBook() {
        return book;
    }

    /**
     * set the state of the sighting
     *
     * @param book of the sighting
     */
    public void setBook(String book) {
        this.book = book;
    }
}
