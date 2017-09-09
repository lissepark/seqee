package dao;

import model.Offer;

import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public interface OfferDAO {
    public List<Offer> findAllOffers();
    public List<Offer>searchOffersByKeyword(String keyWord);
    //public List<Category>findAllCategories();
    public void insert(Offer offer);
    public void update(Offer offer);
    public void delete(Long offerId);
}
