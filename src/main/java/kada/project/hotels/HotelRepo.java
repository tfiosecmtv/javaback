package kada.project.hotels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface HotelEntityRepo extends JpaRepository<HotelEntity, Long> {
}
@Repository
interface HotelFeaturesRepo extends JpaRepository<HotelFeatures, FeaturesId> {
    List<HotelFeatures> findByHotelid(Long hotel_id);
}
@Repository
interface HotelPhoneRepo extends JpaRepository<HotelPhone, PhoneId> {
    List<HotelPhone> findByHotelid(Long hotel_id);
}
@Repository
interface HotelSeasonsRepo extends JpaRepository<HotelSeasons, SeasonsId> {
    List<HotelSeasons> findByHotelid(Long hotel_id);
}

@Repository
interface HotelServicesRepo extends JpaRepository<HotelServices, ServicesId> {
    List<HotelServices> findByHotelid(Long hotel_id);
}