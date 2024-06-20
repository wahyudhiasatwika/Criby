import { Card, CarouselSCHDN, CardIg } from '@/components/commons';
import clsx from 'clsx';
import Image from 'next/image';
import { SiChatbot } from "react-icons/si";
import { AiFillSound } from "react-icons/ai";
import { RiNewspaperFill } from "react-icons/ri";
import { CarouselItem } from '@/components/ui/carousel';
import { Member } from '@/contants/member'
export const metadata = {
    title: 'Criby',
    // description: 'PT. Mega Nusa Indonesia we are MEP Engineers, VAC Engineers, Plumbing Engineers, and Contractors',
    description: 'Kenali kebutuhan bayi anda dan mengerti makna di balik setiap tangisannya',
};

export default function Page() {
    return (
        <div style={{ overflow: 'hidden' }} className={clsx('flex flex-col w-full gap-32')}>
            <div className='flex flex-col gap-6 md:gap-24 justify-center items-center md:px-24 sm:px-14 px-4 bg-custom-background bg-cover bg-center bg-no-repeat min-h-screen' data-aos="zoom-in-up" id="utama">
                <div className='flex flex-col md:flex-row justify-between items-center w-full md:px-24'>
                    <div className='text-center md:text-left md:flex-1 md:pr-12'>
                        <p className='md:text-[40px] text-[25px] font-bold'>Criby</p>
                        <p className='mt-4'>Kenali kebutuhan bayi anda dan mengerti makna di balik setiap tangisannya</p>
                        <a href="https://model.criby.app/download" target="_blank" rel="noopener noreferrer">
                            <button className='mt-6 text-white py-2 px-4 rounded' style={{ backgroundColor: '#7C83FD' }}>Unduh Disini!</button>
                        </a>
                    </div>
                    <div className='mt-6 md:mt-0 md:flex-1 md:pl-12 flex justify-end items-end'>
                        <Image width={0} height={0} src="/image/mockup/mockup.png" alt="Description of image" className='w-full h-auto md:max-w-xs'/>
                    </div>
                </div>
            </div>
            <div className='flex flex-col gap-6 md:gap-24 justify-center items-center md:px-24 sm:px-14 px-4' data-aos="zoom-in-up" id="fitur">
                <p className='md:text-[40px] text-[25px] font-bold text-center'>Fitur pada aplikasi Criby</p>
                <div className='grid md:grid-cols-3 grid-cols-1 gap-8 justify-between items-center'>
                    <Card
                        title='Klasifikasi Suara'
                        description='Kami menyediakan sebuat fitur untuk mengklasifikasi suara tangisan bayi Anda.'
                        icon={AiFillSound}
                    />
                    <Card
                        title='ChatBot'
                        description='Tanyakan pertanyaan anda menggunakan ChatBot kami.'
                        icon={SiChatbot}
                    />
                    <Card
                        title='Referensi Artikel'
                        description='Pelajari lebih lanjut mengenai alasan bayi Anda menangis'
                        icon={RiNewspaperFill}
                    />

                </div>
            </div>
            <div className='flex flex-col gap-6 md:gap-24 w-full h-full justify-center items-center md:px-32 sm:px-14 px-4 bg-custom-background bg-cover bg-center bg-no-repeat min-h-screen py-16' data-aos="zoom-in-up" id='anggota'>
                <p className='md:text-[40px] text-[25px] font-bold text-center'>Anggota</p>
                <CarouselSCHDN
                    opts={{ align: 'center' }}
                    orientation='horizontal'
                    classNameCarousel='w-full w-full'
                    classNameCarouselContent='-mt-1 h-full'
                    classNameCarouselItem='pt-1 md:basis-1/3'
                    classNameCarouselPrevious='!bg-white !text-black'
                    classNameCarouselNext='!bg-white !text-black'
                    data={Array.from({ length: 5 })}
                >
                    {
                        Member.map((item, index) => (
                            <CarouselItem key={index} className={clsx("pt-1 md:basis-1/3")}>
                                <div className="p-1 w-full flex justify-center items-center h-full">
                                    <CardIg
                                        member={item.name}
                                        image={item.image}
                                        description = {item.description}
                                    />
                                </div>
                            </CarouselItem>
                        ))
                    }
                </CarouselSCHDN>
            </div>
        </div>
    );
}
