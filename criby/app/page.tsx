import Image from "next/image";

export default function Home() {
  return (
    <div style={{ overflow: 'hidden' }} className={clsx('flex flex-col w-full gap-32')}>
            <div id="beranda">
                <CustomCarousel2>
                    <div className='w-full'>
                        <section className="relative h-full min-h-[90vh] bg-blue-100 md:p-32 p-4 flex justify-center items-center flex-col bg-center" style={{ backgroundImage: "url('/image/mobil/ayla.jpg')", backgroundSize: "cover" }}>
                            <div className="absolute inset-0 bg-black opacity-50 z-10"></div>
                            <h1 className="md:text-[48px] text-[30px] font-bold mb-2 bg-clip-text text-transparent bg-gradient-to-r from-[#ab0d0e] to-white relative z-20">ASCO DEWI SARTIKA</h1>
                            <p className="text-white text-center text-lg mb-8 relative z-20">Taklukkan Jalan dengan Gaya, Temukan Impian Anda di Asco Dewi Sartika!</p>
                        </section>
                    </div>
                    <div className='w-full'>
                        <section className="relative h-full min-h-[90vh] bg-blue-100 md:p-32 p-4 flex justify-center items-center flex-col bg-center" style={{ backgroundImage: "url('/image/mobil/rocky.jpg')", backgroundSize: "cover" }}>
                            <div className="absolute inset-0 bg-black opacity-50 z-10"></div>
                            <h1 className="md:text-[48px] text-[30px] font-bold mb-2 bg-clip-text text-transparent bg-gradient-to-r from-[#ab0d0e] to-white relative z-20">ASCO DEWI SARTIKA</h1>
                            <p className="text-white text-center text-lg mb-8 relative z-20">Taklukkan Jalan dengan Gaya, Temukan Impian Anda di Asco Dewi Sartika!</p>
                        </section>
                    </div>
                    <div className='w-full'>
                        <section className="relative h-full min-h-[90vh] bg-blue-100 md:p-32 p-4 flex justify-center items-center flex-col bg-center" style={{ backgroundImage: "url('/image/mobil/terios.jpg')", backgroundSize: "cover" }}>
                            <div className="absolute inset-0 bg-black opacity-50 z-10"></div>
                            <h1 className="md:text-[48px] text-[30px] font-bold mb-2 bg-clip-text text-transparent bg-gradient-to-r from-[#ab0d0e] to-white relative z-20">ASCO DEWI SARTIKA</h1>
                            <p className="text-white text-center text-lg mb-8 relative z-20">Taklukkan Jalan dengan Gaya, Temukan Impian Anda di Asco Dewi Sartika!</p>
                        </section>
                    </div>
                </CustomCarousel2>
            </div>
            <div className='flex flex-col gap-6 md:gap-24 justify-center items-center md:px-24 sm:px-14 px-4' data-aos="zoom-in-up" id="tentang-kami">
                <p className='md:text-[40px] text-[25px] font-bold text-center'>Kenapa anda  harus memilih kami?</p>
                <div className='grid md:grid-cols-3 grid-cols-1 gap-8 justify-between items-center'>
                    <Card
                        title='Pengalaman'
                        description='Kami telah berpengalaman dalam industri otomotif, siap membantu Anda menemukan mobil impian Anda.'
                        icon={MdDirectionsCar}
                    />
                    <Card
                        title='Diskon Special'
                        description='Nikmati penawaran istimewa kami untuk Anda.'
                        icon={MdLocalOffer}
                    />
                    <Card
                        title='Layanan Purna Jual yang Baik'
                        description='Kami siap memberikan dukungan penuh setelah Anda membeli mobil.'
                        icon={MdHeadsetMic}
                    />

                </div>
            </div>
            <div className='flex flex-col gap-6 md:gap-24 justify-center items-center md:px-24 sm:px-14 px-4' data-aos="zoom-in-up" id="berita">
                <div className='grid md:grid-cols-2 grid-cols-1 md:gap-14 gap-10 justify-between items-center'>
                    <div className='w-full h-full flex flex-col gap-3'>
                        <video autoPlay muted loop className="rounded object-cover shadow-md w-full h-full">
                            <source src="/image/video/teriosvid.mp4" />
                        </video>
                        <div className='w-full h-auto'>
                            <h1 className="md:text-[25px] text-[20px] font-bold">Kredit Murah</h1>
                            <p className="md:text-[15px] text-[10px]">Nikmati perjalanan tanpa hambatan dengan penawaran kredit murah dari Asco Dewi Sartika. Kami memahami bahwa setiap langkah menuju mobil impian harus mudah dan terjangkau. Dengan opsi pembayaran yang fleksibel dan bunga kompetitif, kami membantu Anda mewujudkan mimpi Anda.</p>
                        </div>
                    </div>
                    <div className='w-full h-full flex flex-col gap-3'>
                        <video autoPlay muted loop className="rounded object-cover shadow-md w-full h-full">
                            <source src="/image/video/sigravid.mp4" />
                        </video>
                        <div className='w-full h-auto'>
                            <h1 className="md:text-[25px] text-[20px] font-bold">Banyak Variant</h1>
                            <p className="md:text-[15px] text-[10px]">Dari yang sporty hingga yang elegan, Asco Dewi Sartika menawarkan berbagai pilihan model yang sesuai dengan gaya hidup dan kebutuhan Anda. Temukan mobil yang sempurna untuk menghadapi setiap petualangan hidup Anda, dan rasakan performa dan kenyamanan yang luar biasa.</p>
                        </div>
                    </div>
                    <div className='w-full h-full flex flex-col gap-3'>
                        <video autoPlay muted loop className="rounded object-cover shadow-md w-full h-full">
                            <source src="/image/video/aylavid.mp4" />
                        </video>
                        <div className='w-full h-auto'>
                            <h1 className="md:text-[25px] text-[20px] font-bold">Banyak Customer</h1>
                            <p className="md:text-[15px] text-[10px]">Bergabunglah dengan jutaan pelanggan puas yang telah memilih Asco Dewi Sartika sebagai mitra perjalanan mereka. Dukungan purna jual yang ramah dan efisien kami membantu Anda menjaga mobil Anda dalam kondisi prima. Jadilah bagian dari komunitas kami, dan rasakan kehangatan keluarga kami.</p>
                        </div>
                    </div>
                    <div className='w-full h-full flex flex-col gap-3'>
                        <video autoPlay muted loop className="rounded object-cover shadow-md w-full h-full">
                            <source src="/image/video/rockyvid.mp4" />
                        </video>
                        <div className='w-full h-auto'>
                            <h1 className="md:text-[25px] text-[20px] font-bold">Banyak Penghargaan</h1>
                            <p className="md:text-[15px] text-[10px]">Kualitas terbaik tidak luput dari perhatian. Asco Dewi Sartika telah meraih berbagai penghargaan bergengsi atas dedikasinya dalam menyediakan mobil yang andal dan berkualitas. Bergabunglah dengan pengguna kami, dan rasakan kebanggaan menjadi bagian dari kesuksesan kami.</p>
                        </div>
                    </div>
                </div>
            </div>
            <div className='flex flex-col gap-6 md:gap-24 w-full h-full justify-center items-center md:px-24 sm:px-14 px-4' data-aos="zoom-in-up" id='pelanggan-kami'>
                <p className='md:text-[40px] text-[25px] font-bold text-center'>Pelanggan Kami</p>
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
                        Customer.map((item, index) => (
                            <CarouselItem key={index} className={clsx("pt-1 md:basis-1/3")}>
                                <div className="p-1 w-full flex justify-center items-center h-full">
                                    <CardIg
                                        buyer={item.name}
                                        // buyer={item.description}
                                        image={item.image}
                                    />
                                </div>
                            </CarouselItem>
                        ))
                    }
                </CarouselSCHDN>
            </div>
            <div className='w-full' id='temukan-kami'>
                <iframe
                    width="100%"
                    height="450"
                    loading="lazy"
                    allowFullScreen
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3966.0975698008397!2d106.86341727576499!3d-6.250873161205331!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x2e69f3d9be446431%3A0xf9ade1a8dffec93a!2sAsco%20Daihatsu%20Dewi%20Sartika!5e0!3m2!1sen!2sid!4v1711600642763!5m2!1sen!2sid"
                ></iframe>
            </div>
        </div>
  );
}
