'use client';
import clsx from 'clsx';
import Link from 'next/link';
import Image from 'next/image';
import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function Header({ className }: Props) {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const router = useRouter();

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
    };

    const handleLinkClick = (e: { preventDefault: () => void }) => {
        e.preventDefault();
        const sectionId = 'hubungi';
        const element = document.getElementById(sectionId);

        if (element) {
            element.scrollIntoView({ behavior: 'smooth' });
        } else {
            router.push(`/#${sectionId}`);
        }

        // Close the menu if needed
        setIsMenuOpen(false);
    };

    const scrolltoHash = function (element_id: string) {
        const element = document.getElementById(element_id)
        element?.scrollIntoView({ behavior: "smooth", block: "end", inline: "nearest" });
    }

    const handleClick = (element_id: string) => {
        scrolltoHash(element_id)
        setIsMenuOpen(false)
    }
    return (
        <header
            id='header'
            className={clsx('header', className, 'fixed w-full z-20 top-0 left-0 md:flex-row xsm:flex-wrap bg-white')}
        >
            <div className='flex flex-row justify-between md:w-auto w-full items-center'>
                <div className='flex items-center flex-row'>
                    <Image
                        src={'/image/logo.png'}
                        alt='logo image'
                        className='w-[80px] object-contain'
                        sizes='100vw'
                        width={0}
                        height={0}
                    />
                    <h1 className='font-semibold text-lg ml-4'>Criby</h1>
                </div>
                <button
                    data-collapse-toggle='navbar-default'
                    type='button'
                    className='inline-flex items-center p-2 ml-3 text-sm rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400'
                    aria-controls='navbar-default'
                    aria-expanded='false'
                    onClick={toggleMenu}>
                    <span className='sr-only'>Open main menu</span>
                    <svg className='w-6 h-6' aria-hidden='true' fill='currentColor' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'>
                        <path
                            fillRule='evenodd'
                            d='M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z'
                            clipRule='evenodd'></path>
                    </svg>
                </button>
            </div>
            <div className={clsx(isMenuOpen ? 'flex' : 'hidden', 'w-full md:block md:w-auto')} id='navbar-default'>
                <ul className='font-medium flex flex-col p-4 md:p-0 mt-4 md:gap-0 gap-2 md:flex-row md:space-x-1 lg:space-x-8 md:mt-0 md:border-0 md:space-y-0 xsm:space-y-4 md:bg-white'>
                    {/* <li>
                        <Link className='ml-2 font-medium lg:text-base md:text-sm' href='/' onClick={() => setIsMenuOpen(false)}>
                            Home
                        </Link>
                    </li>
                    <li>
                        <Link className='ml-2 font-medium lg:text-base md:text-sm' href='/services' onClick={() => setIsMenuOpen(false)}>
                            Services
                        </Link>
                    </li>
                    <li>
                        <Link className='ml-2 font-medium lg:text-base md:text-sm' href='/portfolios' onClick={() => setIsMenuOpen(false)}>
                            Portfolio
                        </Link>
                    </li>
                    <li>
                        <Link
                            className='ml-2 font-medium lg:text-base md:text-sm bg-[#171E26] text-white px-4 py-2 rounded-xl'
                            href='#'
                            onClick={() => setIsMenuOpen(false)}>
                            Contact Us
                        </Link>
                    </li> */}
                    <li>
                        <div className='cursor-pointer ml-2 font-medium lg:text-base md:text-sm' onClick={() => handleClick('utama')}>
                            Tentang Kami
                        </div>
                    </li>
                    <li>
                        <div className='cursor-pointer ml-2 font-medium lg:text-base md:text-sm' onClick={() => handleClick('fitur')}>
                            Fitur
                        </div>
                    </li>
                    <li>
                        <div className='cursor-pointer ml-2 font-medium lg:text-base md:text-sm' onClick={() => handleClick('anggota')}>
                            Anggota
                        </div>
                    </li>
                    {/* <li>
                        <Link className='ml-2 font-medium lg:text-base md:text-sm' href='/company-profile' onClick={() => setIsMenuOpen(false)}>
                            Company Profile
                        </Link>
                    </li> */}
                </ul>
            </div>
        </header>
    );
}

type Props = {
    className?: string;
};
