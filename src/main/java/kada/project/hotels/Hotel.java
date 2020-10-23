package kada.project.hotels;
import java.util.List;
public class Hotel {
    HotelEntity hotelEntity;
    List<HotelFeatures> hotelFeatures;
    List<HotelPhone> hotelPhones;
    List<HotelSeasons> hotelSeasons;
    List<HotelServices> hotelServices;

    Hotel(HotelEntity hotelEntity, List<HotelFeatures> hotelFeatures, List<HotelPhone> hotelPhones, List<HotelSeasons> hotelSeasons, List<HotelServices> hotelServices) {
        this.hotelEntity = hotelEntity;
        this.hotelFeatures = hotelFeatures;
        this.hotelPhones = hotelPhones;
        this.hotelSeasons = hotelSeasons;
        this.hotelServices = hotelServices;
    }
}
