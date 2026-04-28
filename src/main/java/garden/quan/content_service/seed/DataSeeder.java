package garden.quan.content_service.seed;

import garden.quan.content_service.gallery.GalleryImage;
import garden.quan.content_service.gallery.GalleryRepository;
import garden.quan.content_service.journal.JournalEntry;
import garden.quan.content_service.journal.JournalRepository;
import garden.quan.content_service.offering.Offering;
import garden.quan.content_service.offering.OfferingHighlight;
import garden.quan.content_service.offering.OfferingRepository;
import garden.quan.content_service.plant.Plant;
import garden.quan.content_service.plant.PlantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Seeds the four domains with the same content the frontend ships with,
 * so a fresh DB serves the Angular app immediately.
 *
 * <p>Runs only when each table is empty — safe to leave on across restarts.
 * Active in {@code dev} (and any profile that doesn't disable it).</p>
 */
@Component
@Profile("!noseed")
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final PlantRepository plants;
    private final JournalRepository journal;
    private final GalleryRepository gallery;
    private final OfferingRepository offerings;

    public DataSeeder(PlantRepository plants,
                      JournalRepository journal,
                      GalleryRepository gallery,
                      OfferingRepository offerings) {
        this.plants = plants;
        this.journal = journal;
        this.gallery = gallery;
        this.offerings = offerings;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedPlants();
        seedJournal();
        seedGallery();
        seedOfferings();
    }

    // ---------------------------------------------------------------- Plants

    private void seedPlants() {
        if (plants.count() > 0) return;
        log.info("Seeding plants…");

        plants.save(plant("Climbing Rose", "Hoa hồng leo",
            "Rosa \"Cécile Brünner\"", "climber", "Rosaceae",
            "full-sun", "moderate", "moderate",
            List.of("late spring", "summer"),
            "3–4 m", null,
            "Trained against the south wall. Light pruning in February.",
            "Cho leo dọc bức tường nam. Tỉa nhẹ vào tháng Hai.",
            "Cuttings from grandmother's garden, taken in 2019. The first flush each May feels like a letter from her.",
            "Cành giâm từ vườn của bà ngoại, lấy vào năm 2019. Đợt nở đầu tiên mỗi tháng Năm như một lá thư từ bà.",
            LocalDate.of(2019, 4, 12), "#c4837a", "rose",
            List.of("heritage", "climber", "fragrant")));

        plants.save(plant("Lavender", "Oải hương",
            "Lavandula angustifolia \"Hidcote\"", "flowering", "Lamiaceae",
            "full-sun", "low", "easy",
            List.of("summer"),
            "60 cm", null,
            "Hard prune after flowering. Bees love it.",
            "Tỉa mạnh sau khi tàn hoa. Ong rất thích.",
            "A hedge of nine plants flanking the gravel path. Their evening scent in July is the whole point of summer.",
            "Một hàng rào chín cây hai bên lối sỏi. Hương buổi chiều tháng Bảy là cả ý nghĩa của mùa hè.",
            LocalDate.of(2020, 5, 3), "#7a6b9c", "lavender",
            List.of("drought-tolerant", "pollinators", "fragrant")));

        plants.save(plant("Japanese Maple", "Phong Nhật",
            "Acer palmatum \"Bloodgood\"", "tree", "Sapindaceae",
            "partial-shade", "moderate", "moderate",
            List.of(),
            "4 m", null,
            "Sheltered from harsh afternoon sun. Burgundy throughout the season.",
            "Che chắn khỏi nắng gắt buổi chiều. Sắc đỏ tía suốt cả mùa.",
            "Planted to anchor the east corner. Watching it leaf out in April is one of the year's best small joys.",
            "Trồng để giữ góc đông. Ngắm nó nảy lá vào tháng Tư là một trong những niềm vui nhỏ đẹp nhất của năm.",
            LocalDate.of(2018, 3, 22), "#8b3a2f", "maple",
            List.of("specimen", "autumn-colour", "shade")));

        plants.save(plant("Lemon Thyme", "Húng tây chanh",
            "Thymus citriodorus", "herb", "Lamiaceae",
            "full-sun", "low", "easy",
            List.of(),
            "15 cm", null,
            "Edges the herb spiral. Trim handfuls into roast vegetables.",
            "Viền quanh xoắn ốc thảo mộc. Cắt từng nắm cho rau nướng.",
            "Grown from a single supermarket pot in 2021. Now spreads through the gravel like it owns the place.",
            "Lớn từ một chậu nhỏ mua siêu thị năm 2021. Giờ lan ra khắp lối sỏi như chủ của nó.",
            LocalDate.of(2021, 6, 9), "#9bb069", "thyme",
            List.of("edible", "ground-cover", "culinary")));

        plants.save(plant("Heart-leaf Philodendron", "Trầu bà lá tim",
            "Philodendron hederaceum", "foliage", "Araceae",
            "shade", "moderate", "easy",
            List.of(),
            "trailing 2 m+", "thân leo 2 m+",
            "Lives indoors near the kitchen window. Trails along the dresser.",
            "Để trong nhà gần cửa sổ bếp. Buông dài theo tủ ngăn.",
            "A cutting from a friend's wedding centerpiece, 2022. Its growth marks the months we've been at this address.",
            "Một cành giâm từ bình hoa cưới của người bạn, năm 2022. Sự lớn lên của nó đánh dấu những tháng chúng tôi sống ở địa chỉ này.",
            LocalDate.of(2022, 9, 14), "#3d6e3a", "philo",
            List.of("indoor", "low-light", "trailing")));

        plants.save(plant("Foxglove", "Mao địa hoàng",
            "Digitalis purpurea", "flowering", "Plantaginaceae",
            "partial-shade", "moderate", "easy",
            List.of("early summer"),
            "1.2 m", null,
            "Self-seeds reliably along the back border. Toxic to handle without gloves.",
            "Tự gieo hạt đều dọc luống sau. Có độc, hãy dùng găng tay khi cầm.",
            "Started from a packet of mixed seed in 2020 and have been with us ever since, choosing their own spots.",
            "Bắt đầu từ một gói hạt giống pha trộn năm 2020 và ở lại với chúng tôi từ đó, tự chọn chỗ mọc.",
            LocalDate.of(2020, 4, 1), "#a85d8c", "foxglove",
            List.of("cottage-garden", "self-seeding", "pollinators")));

        plants.save(plant("Hosta", "Hoa Ngọc trâm",
            "Hosta \"Sum and Substance\"", "foliage", "Asparagaceae",
            "shade", "high", "easy",
            List.of(),
            "70 cm", null,
            "Slug bait essential in April. Worth the trouble.",
            "Cần đặt mồi ốc sên vào tháng Tư. Bõ công.",
            "Three clumps under the maple. Their unfurling spears are the first sign winter is over.",
            "Ba khóm dưới gốc phong. Những mầm bung là dấu hiệu đầu tiên rằng mùa đông đã qua.",
            LocalDate.of(2019, 4, 30), "#a8b85a", "hosta",
            List.of("shade", "foliage-focus")));

        plants.save(plant("Tomato \"Black Krim\"", "Cà chua \"Black Krim\"",
            "Solanum lycopersicum", "edible", "Solanaceae",
            "full-sun", "high", "moderate",
            List.of(),
            "1.8 m staked", "1,8 m có cọc",
            "Started under glass in March. Three plants in the south bed, mulched with comfrey.",
            "Gieo dưới kính từ tháng Ba. Ba cây ở luống nam, phủ rơm bằng cây liên mộc.",
            "The flavour of August. Eaten warm from the vine over the kitchen sink — a private ritual.",
            "Hương vị của tháng Tám. Ăn ấm ngay tại dây trên bồn rửa bếp — một nghi thức riêng.",
            LocalDate.of(2024, 5, 15), "#7a3a3a", "tomato",
            List.of("heirloom", "edible", "annual")));

        plants.save(plant("Echeveria", "Sen đá Echeveria",
            "Echeveria \"Lola\"", "succulent", "Crassulaceae",
            "full-sun", "low", "easy",
            List.of(),
            "15 cm", null,
            "Lives in a terracotta dish on the kitchen windowsill.",
            "Trồng trong đĩa đất nung trên bệ cửa sổ bếp.",
            "A small thing. Its rosettes catch the morning light just so, and that's enough.",
            "Một điều nhỏ bé. Những bông tròn của nó hứng nắng sớm vừa đẹp, và thế là đủ.",
            LocalDate.of(2023, 8, 20), "#b8a8c8", "echeveria",
            List.of("indoor", "low-water", "sculptural")));

        log.info("Seeded {} plants", plants.count());
    }

    private Plant plant(String nameEn, String nameVi, String latin, String category, String family,
                        String light, String water, String difficulty,
                        List<String> bloomSeason,
                        String heightEn, String heightVi,
                        String notesEn, String notesVi,
                        String storyEn, String storyVi,
                        LocalDate plantedOn, String color, String illustration,
                        List<String> tags) {
        return Plant.builder()
            .commonNameEn(nameEn).commonNameVi(nameVi)
            .latinName(latin).category(category).family(family)
            .light(light).water(water).difficulty(difficulty)
            .bloomSeason(new ArrayList<>(bloomSeason))
            .heightEn(heightEn).heightVi(heightVi)
            .notesEn(notesEn).notesVi(notesVi)
            .storyEn(storyEn).storyVi(storyVi)
            .plantedOn(plantedOn).color(color).illustration(illustration)
            .tags(new ArrayList<>(tags))
            .build();
    }

    // --------------------------------------------------------------- Journal

    private void seedJournal() {
        if (journal.count() > 0) return;
        log.info("Seeding journal entries…");

        journal.save(JournalEntry.builder()
            .date(LocalDate.of(2026, 4, 18)).season("spring")
            .titleEn("The maple unfurls")
            .titleVi("Cây phong bung lá")
            .excerptEn("Watching the Bloodgood unfold its first leaves felt like watching a slow argument resolve.")
            .excerptVi("Ngắm cây Bloodgood mở những chiếc lá đầu tiên giống như xem một cuộc tranh luận chậm rãi được hoá giải.")
            .bodyEn("""
                Eight years in and the Acer still surprises me. This morning, coffee in hand, I noticed the bud caps had cracked overnight — burgundy fingertips reaching out into a damp grey sky.

                The lavender hedge is greening at the base; nothing flowering yet, but the rosemary has been buzzing with bees for a week. I cleared the last of the winter mulch from the rose bed and found a crocus already finished, a private festival I missed.

                A reminder, again: the garden does not wait for me to be looking.""")
            .bodyVi("""
                Tám năm rồi mà cây phong vẫn làm tôi ngạc nhiên. Sáng nay, cốc cà phê trên tay, tôi thấy những vảy nụ đã nứt qua đêm — những đầu ngón tay đỏ tía vươn ra giữa bầu trời xám ẩm.

                Hàng rào oải hương đã xanh dần ở gốc; chưa có gì nở, nhưng cây hương thảo đã rộn tiếng ong cả tuần nay. Tôi dọn nốt lớp phủ rơm mùa đông khỏi luống hồng và thấy một bông nghệ tây đã tàn rồi, một lễ hội riêng tôi đã bỏ lỡ.

                Lại một lời nhắc nữa: khu vườn không đợi tôi nhìn ngắm nó.""")
            .weatherEn("11°C, overcast, light rain by afternoon")
            .weatherVi("11°C, âm u, mưa nhẹ về chiều")
            .mood("patient").readMinutes(3)
            .build());

        journal.save(JournalEntry.builder()
            .date(LocalDate.of(2026, 3, 22)).season("spring")
            .titleEn("Sowing under glass")
            .titleVi("Gieo hạt dưới kính")
            .excerptEn("Tomatoes, sweet peas, cosmos. Three trays on the kitchen windowsill, hoping for warmth.")
            .excerptVi("Cà chua, đậu hoa, cúc vạn thọ. Ba khay trên bệ cửa sổ bếp, hy vọng có hơi ấm.")
            .bodyEn("""
                The seed catalogues did their work in February — every year I order more than I can possibly grow. This week the tomatoes went in: Black Krim again, of course, plus a green-shouldered variety I haven't tried.

                The kitchen smells like compost and is faintly lit by a borrowed grow lamp. It is, in the best sense, a chaotic time of year.""")
            .bodyVi("""
                Các catalô hạt giống đã làm xong việc của chúng vào tháng Hai — mỗi năm tôi đặt nhiều hơn mức có thể trồng. Tuần này tôi đã gieo cà chua: Black Krim như mọi khi, cộng thêm một giống vai xanh tôi chưa thử.

                Căn bếp thoảng mùi phân ủ và được thắp sáng nhẹ bởi chiếc đèn trồng cây mượn được. Đây, theo nghĩa đẹp nhất, là một khoảng thời gian hỗn loạn của năm.""")
            .weatherEn("6°C, bright").weatherVi("6°C, nắng trong")
            .mood("hopeful").readMinutes(2)
            .build());

        journal.save(JournalEntry.builder()
            .date(LocalDate.of(2025, 10, 4)).season("autumn")
            .titleEn("The garden goes quiet")
            .titleVi("Khu vườn lặng dần")
            .excerptEn("Cutting back the dahlias and letting the seedheads stand. Already thinking of next year.")
            .excerptVi("Cắt thược dược và để những đầu hạt đứng yên. Đã nghĩ đến năm sau.")
            .bodyEn("""
                October is the month of editing. I cut the dahlias hard, lifted the tubers, and labelled them with a marker that has, by now, written hundreds of names.

                The grasses are at their best in the low slanting light of late afternoon, and the rugosa hips are properly red. I left the teasels for the goldfinches.""")
            .bodyVi("""
                Tháng Mười là tháng biên tập. Tôi cắt thược dược thật mạnh, đào củ, và dán nhãn bằng cây bút marker đến giờ đã viết hàng trăm cái tên.

                Những bụi cỏ đẹp nhất trong ánh chiều xiên thấp cuối ngày, và quả hồng Rugosa đã đỏ đúng độ. Tôi để lại những bụi gai tế cho lũ chim sẻ vàng.""")
            .weatherEn("14°C, golden").weatherVi("14°C, vàng óng")
            .mood("reflective").readMinutes(4)
            .build());

        journal.save(JournalEntry.builder()
            .date(LocalDate.of(2025, 7, 12)).season("summer")
            .titleEn("Peak rose, peak everything")
            .titleVi("Đỉnh hoa hồng, đỉnh tất cả")
            .excerptEn("The Cécile Brünner is a cloud against the south wall and the bees do not stop.")
            .excerptVi("Cây Cécile Brünner như một đám mây bên tường nam và lũ ong không ngừng nghỉ.")
            .bodyEn("""
                If you stand by the back door at dusk in mid-July, the air is thick with rose and lavender and the small papery sound of bees finishing their work.

                Watered everything deeply at 9pm. The clay holds it well; I water less here than anywhere I have lived.""")
            .bodyVi("""
                Nếu bạn đứng bên cửa sau lúc chạng vạng giữa tháng Bảy, không khí đặc mùi hồng và oải hương cùng tiếng giấy nhỏ của ong đang hoàn thành công việc.

                Tưới đẫm mọi thứ lúc 9 giờ tối. Đất sét giữ nước tốt; ở đây tôi tưới ít hơn bất cứ nơi nào tôi từng sống.""")
            .weatherEn("26°C, still").weatherVi("26°C, lặng gió")
            .mood("grateful").readMinutes(3)
            .build());

        log.info("Seeded {} journal entries", journal.count());
    }

    // --------------------------------------------------------------- Gallery

    private void seedGallery() {
        if (gallery.count() > 0) return;
        log.info("Seeding gallery images…");

        List<GalleryImage> imgs = List.of(
            gal("The rose against the south wall, June",  "Hoa hồng tựa tường nam, tháng Sáu",
                "summer", 2025, "portrait",  "#c4837a", "#5d3a4d", "rose-wall"),
            gal("Path through the lavender, evening",     "Lối đi qua oải hương, buổi tối",
                "summer", 2025, "landscape", "#7a6b9c", "#2d4a32", "lavender-path"),
            gal("Maple in October light",                  "Cây phong dưới ánh tháng Mười",
                "autumn", 2024, "square",    "#b8593a", "#3d2f24", "maple-leaf"),
            gal("Tomatoes ripening on the kitchen sill",   "Cà chua chín trên bệ cửa sổ bếp",
                "summer", 2025, "landscape", "#c89b3a", "#7a3a3a", "tomato-still"),
            gal("Hosta unfurling in April",                "Cây ngọc trâm bung lá tháng Tư",
                "spring", 2025, "portrait",  "#a8b85a", "#2d4a32", "hosta-unfurl"),
            gal("Frost on the seedheads, December",        "Sương giá trên đầu hạt, tháng Chạp",
                "winter", 2024, "landscape", "#d9cdb1", "#4a6b4f", "frost"),
            gal("Foxgloves at the back border",            "Mao địa hoàng ở luống sau",
                "summer", 2025, "portrait",  "#a85d8c", "#3d6e3a", "foxglove-tall"),
            gal("The herb spiral, midsummer",              "Xoắn ốc thảo mộc, giữa hè",
                "summer", 2024, "square",    "#9bb069", "#6b5544", "herb-spiral"),
            gal("First snow on the maple",                 "Tuyết đầu trên cây phong",
                "winter", 2024, "landscape", "#faf6ec", "#1a2e1f", "snow-maple")
        );
        gallery.saveAll(imgs);
        log.info("Seeded {} gallery images", gallery.count());
    }

    private GalleryImage gal(String capEn, String capVi, String season, int year,
                             String ratio, String from, String to, String illustration) {
        return GalleryImage.builder()
            .captionEn(capEn).captionVi(capVi)
            .season(season).year(year).ratio(ratio)
            .paletteFrom(from).paletteTo(to)
            .illustration(illustration)
            .build();
    }

    // ------------------------------------------------------------- Offerings

    private void seedOfferings() {
        if (offerings.count() > 0) return;
        log.info("Seeding offerings…");

        offerings.save(offering(
            "A vegetarian supper at your table",
            "Bữa tối chay tại bàn nhà bạn",
            "cooking",
            "Three courses, mostly from this garden, cooked in your kitchen.",
            "Ba món, phần lớn từ khu vườn này, nấu ngay trong bếp nhà bạn.",
            "I bring the garden with me — whatever is in season that week — and cook a slow, vegetable-led supper for you and your people. A grain or pulse, a roasted thing, a green thing, a small sweet at the end. Plates left clean, kitchen left clean.",
            "Tôi mang khu vườn theo — bất cứ gì đang vào mùa tuần đó — và nấu một bữa tối chậm rãi, lấy rau làm chính cho bạn và người thân. Một món ngũ cốc hoặc đậu, một món nướng, một món xanh, một món ngọt nhỏ cuối bữa. Đĩa sạch, bếp sạch.",
            new BigDecimal("95.00"), "£",
            "approx. 4 hours", "khoảng 4 giờ",
            "2–8 guests", "2–8 khách",
            "thyme", "#9bb069",
            List.of(
                "Menu agreed in advance, written by hand",
                "Produce from this garden where the season allows",
                "Vegetarian by default; vegan and gluten-free easy",
                "I shop, cook, serve, and wash up"
            ),
            List.of(
                "Thực đơn thống nhất trước, viết tay",
                "Nguyên liệu từ khu vườn khi mùa cho phép",
                "Mặc định là chay; thuần chay và không gluten dễ làm",
                "Tôi đi chợ, nấu, dọn bàn, và rửa bát"
            )));

        offerings.save(offering(
            "A Sunday lunch, brought to you",
            "Bữa trưa Chủ Nhật, mang đến tận nhà",
            "delivery",
            "A whole vegetarian Sunday, in baskets, ready to warm and serve.",
            "Cả bữa Chủ Nhật chay, đóng giỏ, sẵn sàng hâm và dọn ra.",
            "For Sundays you'd like to keep simple: a proper roast in the vegetarian key — a stuffed squash or a celeriac wellington — with all the sides, gravy, a salad, and a pudding. Delivered cold, in tins and kilner jars, with a small card telling you exactly what to do.",
            "Cho những Chủ Nhật bạn muốn đơn giản: một món nướng đúng nghĩa theo phong cách chay — bí nhồi hoặc bánh celeriac wellington — kèm đủ món phụ, nước sốt, salad và món tráng miệng. Giao lạnh, trong hộp thiếc và lọ kilner, với một tấm thiệp nhỏ chỉ rõ cách làm.",
            new BigDecimal("18.00"), "£",
            "delivered Saturday afternoon", "giao chiều thứ Bảy",
            "per person · min. 4", "mỗi người · tối thiểu 4",
            "tomato", "#b8593a",
            List.of(
                "Centrepiece + 4 sides + pudding",
                "Heating instructions on a hand-written card",
                "All containers returnable",
                "Within 8 miles of the garden"
            ),
            List.of(
                "Món chính + 4 món phụ + món tráng miệng",
                "Hướng dẫn hâm ghi tay trên thiệp",
                "Mọi hộp đều trả lại được",
                "Trong vòng 13 km quanh khu vườn"
            )));

        offerings.save(offering(
            "Today's harvest box",
            "Giỏ rau quả hôm nay",
            "delivery",
            "A weekly box of whatever the garden has given up that morning.",
            "Một giỏ hàng tuần, gồm bất cứ gì khu vườn cho sáng hôm đó.",
            "Each Saturday morning I pack a box of what the garden has — fruit, veg, leaves, herbs, a few flowers, sometimes a jar of something. No two boxes are alike. Subscribe and one will appear at your door, or come and collect from the gate.",
            "Sáng thứ Bảy nào tôi cũng đóng một giỏ những gì khu vườn có — trái, rau, lá, thảo mộc, vài nhành hoa, đôi khi một lọ gì đó. Không giỏ nào giống giỏ nào. Đăng ký và một giỏ sẽ xuất hiện trước cửa nhà bạn, hoặc ghé cổng nhận.",
            new BigDecimal("22.00"), "£",
            "weekly", "hàng tuần",
            "feeds 2 well", "đủ cho 2 người ăn thoải mái",
            "hosta", "#a8b85a",
            List.of(
                "Picked the morning of delivery",
                "Eight-ish items, varies with the season",
                "Pause any week, no contract",
                "Recipe card in every box"
            ),
            List.of(
                "Hái sáng cùng ngày giao",
                "Khoảng tám món, thay đổi theo mùa",
                "Tạm ngưng tuần nào cũng được, không hợp đồng",
                "Mỗi giỏ có thiệp công thức nấu"
            )));

        offerings.save(offering(
            "A morning in the kitchen",
            "Một buổi sáng trong bếp",
            "workshop",
            "Small, hands-on cooking sessions in the garden kitchen.",
            "Lớp nấu ăn nhỏ, tự tay làm trong bếp vườn.",
            "Three or four people around the kitchen table, learning one thing properly: a sourdough rhythm, jam-making, fresh pasta from scratch, or a season's worth of preserving. We start with coffee and end with lunch, eaten together.",
            "Ba hoặc bốn người quanh bàn bếp, học một thứ cho ra đầu ra đũa: nhịp men chua, làm mứt, mì pasta tươi từ đầu, hoặc cả một mùa làm đồ ngâm. Bắt đầu bằng cà phê và kết thúc bằng bữa trưa, cùng ăn.",
            new BigDecimal("65.00"), "£",
            "3 hours", "3 giờ",
            "up to 4 people", "tối đa 4 người",
            "lavender", "#7a6b9c",
            List.of(
                "Topic agreed when you book",
                "All ingredients and aprons provided",
                "You take home what you've made",
                "Lunch and tea included"
            ),
            List.of(
                "Chủ đề thống nhất khi đặt lớp",
                "Có sẵn nguyên liệu và tạp dề",
                "Mang về nhà những gì bạn đã làm",
                "Bữa trưa và trà bao gồm"
            )));

        offerings.save(offering(
            "A garden, planned with you",
            "Một khu vườn, lên kế hoạch cùng bạn",
            "consulting",
            "A morning in your garden, talking through what could grow there.",
            "Một buổi sáng tại vườn nhà bạn, cùng bàn xem có thể trồng gì.",
            "I come and walk your garden with you, mug in hand, and we talk about what you want from it — what to keep, what to move, what to plant, what to let be. You leave with a hand-drawn plan and a season-by-season list of things to do.",
            "Tôi đến và đi quanh khu vườn cùng bạn, cốc trà trên tay, và mình nói về điều bạn muốn — giữ gì, dời gì, trồng gì, để gì yên. Bạn nhận được một bản vẽ tay và một danh sách việc cần làm theo từng mùa.",
            new BigDecimal("120.00"), "£",
            "2–3 hours + written plan", "2–3 giờ + bản kế hoạch",
            null, null,
            "maple", "#8b3a2f",
            List.of(
                "On-site walk-through and conversation",
                "Hand-drawn plan, posted within a week",
                "Plant list with sources and quantities",
                "A follow-up call a month later"
            ),
            List.of(
                "Đi xem tận nơi và trò chuyện",
                "Bản vẽ tay, gửi trong vòng một tuần",
                "Danh sách cây kèm nguồn và số lượng",
                "Một cuộc gọi theo dõi sau một tháng"
            )));

        log.info("Seeded {} offerings", offerings.count());
    }

    private Offering offering(String titleEn, String titleVi, String category,
                              String taglineEn, String taglineVi,
                              String descEn, String descVi,
                              BigDecimal price, String currency,
                              String durationEn, String durationVi,
                              String servesEn, String servesVi,
                              String illustration, String color,
                              List<String> highlightsEn, List<String> highlightsVi) {
        Offering o = Offering.builder()
            .titleEn(titleEn).titleVi(titleVi)
            .category(category)
            .taglineEn(taglineEn).taglineVi(taglineVi)
            .descriptionEn(descEn).descriptionVi(descVi)
            .priceFrom(price).currency(currency)
            .durationEn(durationEn).durationVi(durationVi)
            .servesEn(servesEn).servesVi(servesVi)
            .illustration(illustration).color(color)
            .highlights(new ArrayList<>())
            .build();
        List<OfferingHighlight> hs = new ArrayList<>();
        for (int i = 0; i < highlightsEn.size(); i++) {
            hs.add(OfferingHighlight.builder()
                .position(i)
                .textEn(highlightsEn.get(i))
                .textVi(i < highlightsVi.size() ? highlightsVi.get(i) : null)
                .build());
        }
        o.replaceHighlights(hs);
        return o;
    }
}
