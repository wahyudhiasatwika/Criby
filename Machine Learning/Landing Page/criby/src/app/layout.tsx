import clsx from 'clsx';
import '@/styles/index.scss';
import { POPPINS, ROBOTO_MONO } from '@/contants/fonts';

import DefaultLayout from '@/components/layouts/default-layouts';

export const metadata = {
    title: {
        default: 'Criby',
        template: '%s | Criby',
    },
    description: 'Kenali kebutuhan bayi anda dan mengerti makna di balik setiap tangisannya.',
};

export default function RootLayout({ children }: Props) {
    return (
        <html lang='en' className={clsx('scroll-smooth', POPPINS.variable, ROBOTO_MONO.variable)}>
            <DefaultLayout>{children}</DefaultLayout>
        </html>
    );
}

type Props = {
    children: React.ReactNode;
};
