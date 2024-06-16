'use client';
import Slider from "react-slick";
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { IoIosArrowForward, IoIosArrowBack } from 'react-icons/io';
const SlickArrowLeft = ({ currentSlide, slideCount, ...props }: any) => (
    <button
        {...props}
        className={
            "slick-prev slick-arrow z-50 hidden" +
            (currentSlide === 0 ? " slick-disabled" : "")
        }
        aria-hidden="true"
        aria-disabled={currentSlide === 0 ? true : false}
        type="button"
    >
        <IoIosArrowBack className="hidden w-6 h-6 text-white absolute top-0 left-5 rounded-full bg-custom-pink z-50" />
    </button>
);
const SlickArrowRight = ({ currentSlide, slideCount, ...props }: any) => (
    <button
        {...props}
        className={
            "slick-next slick-arrow z-50 hidden" +
            (currentSlide === slideCount - 1 ? " slick-disabled" : "")
        }
        aria-hidden="true"
        aria-disabled={currentSlide === slideCount - 1 ? true : false}
        type="button"
    >
        <IoIosArrowForward className="hidden w-6 h-6 text-white absolute right-[21px] top-0 rounded-full bg-custom-pink z-50" />,
    </button>
);

function CustomCarousel2({ children }: any) {
    // const sliderRef = useRef(null);
    // const footerStyle = {
    //     background: `#212844 url('/images/blackmask.webp') no-repeat right center`,
    //     backgroundSize: '40% auto'
    // };
    // const renderArrows = () => {
    //     return (
    //         <div className="slider-arrow">
    //             <ButtonBase
    //                 className="arrow-btn prev"
    //                 onClick={() => sliderRef.current.slickPrev()}
    //             >
    //                 <ArrowLeft />
    //             </ButtonBase>
    //             <ButtonBase
    //                 className="arrow-btn next"
    //                 onClick={() => sliderRef.current.slickNext()}
    //             >
    //                 <ArrowRight />
    //             </ButtonBase>
    //         </div>
    //     );
    // };
    const settings = {
        // dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        cssEase: "linear",
        arrows: false,
        // prevArrow: <SlickArrowLeft />,
        // nextArrow: <SlickArrowRight />,
    };
    return (
        <div className="w-full">
            <Slider {...settings}>
                {
                    children
                }
            </Slider>
        </div>
    )
}

export default CustomCarousel2;