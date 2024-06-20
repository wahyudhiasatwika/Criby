'use client';
import AOS from 'aos';
import 'aos/dist/aos.css';
import { useEffect } from 'react';
import dynamic from 'next/dynamic';

const Header = dynamic(() => import('./header/header'));
const Footer = dynamic(() => import('./footer/footer'));
// const FloatingWhatsapp = dynamic(() => import('./floating-whatsapp/floating-whtaspp'), { ssr: false });

export default function DefaultLayout({ children }: Props) {
    useEffect(() => {
        AOS.init({});
    }, []);

    return (
        <body id='layout-default'>
            <Header />
            {/* <FloatingWhatsapp /> */}
            <main className='main'>{children}</main>
            <Footer />
        </body>
    );
}

type Props = {
    children: React.ReactNode;
};
