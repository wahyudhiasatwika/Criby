'use client';
import clsx from 'clsx';
import Image from 'next/image';
import ImageWithFallback from '@/components/commons/image-with-fallback/image-with-fallback';
export default function Footer({ className }: Props) {
    // const [language, setLanguage] = useState('en');

    // const toggleLanguage = () => {
    //     const newLanguage = language === 'en' ? 'ind' : 'en';
    //     setLanguage(newLanguage);
    // };
    const scrolltoHash = function (element_id: string) {
        const element = document.getElementById(element_id)
        element?.scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
    }
    return (
<footer id='footer' className={clsx('footer', className)} style={{ padding: '28px 96px' }}>
    <div className='flex md:flex-row flex-col justify-between items-center w-full py-'>
        <div className='flex flex-row justify-between items-center w-full'>
            <div className='md:ml-auto xsm:mt-4 md:mt-4 flex items-center justify-start w-[50%] md:w-[70%]'>
                <div className='flex md:flex-row flex-col md:gap-4 md:space-y-0 xsm:space-y-4 md:text-left'>
                    <div className='text-black text-center md:text-left'>
                        <span className='cursor-pointer m-0 xsm:leading-loose md:text-base sm:text-sm xsm:text-xs flex flex-auto gap-2 justify-center items-center' onClick={() => scrolltoHash('utama')}>Tentang Kami</span>
                    </div>
                    <span className="text-black opacity-50 mx-1 md:block hidden">|</span>
                    <div className='text-black text-center md:text-left'>
                        <span className='cursor-pointer m-0 xsm:leading-loose md:text-base sm:text-sm xsm:text-xs flex flex-auto gap-2 justify-center items-center' onClick={() => scrolltoHash('fitur')}>Fitur</span>
                    </div>
                    <span className="text-black opacity-50 mx-1 md:block hidden">|</span>
                    <div className='text-black text-center md:text-left'>
                        <span className='cursor-pointer m-0 xsm:leading-loose md:text-base sm:text-sm xsm:text-xs flex flex-auto gap-2 justify-center items-center' onClick={() => scrolltoHash('anggota')}>Anggota</span>
                    </div>
                </div>
            </div>
            <div className='flex flex-row justify-between gap-2 items-start w-[50%] md:w-[30%]'>
                <div className='flex items-center flex-row'>
                    <ImageWithFallback
                        src={'/image/logo.png'}
                        alt='logo image'
                        className='w-[60px] object-contain'
                        sizes='100vw'
                        priority={true}
                        width={0}
                        height={0}
                    />
                    <h1 className='font-semibold text-m ml-2'>Criby</h1>
                </div>
                <div className='flex items-center flex-row'>
                    <ImageWithFallback
                        src={'/image/bangkit.png'}
                        alt='logo image'
                        className='w-[100px] object-contain'
                        sizes='100vw'
                        priority={true}
                        width={0}
                        height={0}
                    />
                </div>
                <div className='flex items-center flex-row'>
                    <ImageWithFallback
                        src={'/image/kampus merdeka.png'}
                        alt='logo image'
                        className='w-[100px] object-contain'
                        sizes='100vw'
                        priority={true}
                        width={0}
                        height={0}
                    />
                </div>
            </div>
        </div>
    </div>
    <p className='text-black w-full md:text-left text-center md:mt-2 xsm:mt-2 mb-6 xsm:leading-loose font-extralight md:text-base sm:text-sm xsm:text-xs'>
        © Criby - Bangkit Academy. All Rights Reserved.
    </p>
</footer>



    );
}

type Props = {
    className?: string;
};
