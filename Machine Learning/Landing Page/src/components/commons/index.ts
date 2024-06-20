import dynamic from 'next/dynamic';

const Toggle = dynamic(() => import('./button/button'));
const ImageWithFallback = dynamic(() => import('./image-with-fallback/image-with-fallback'));
const CustomCarousel2 = dynamic(() => import('./slider/custom'));
const CarouselSCHDN = dynamic(() => import('./carousel/carousel'));
const Card = dynamic(() => import('./card/card'));
const CardIg = dynamic(() => import('./card/cardIG'));

export {CardIg, Card, CarouselSCHDN, CustomCarousel2, Toggle, ImageWithFallback };
