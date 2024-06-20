'use client';
import clsx from 'clsx';
import Image, { ImageProps } from 'next/image';
import { useEffect, useState } from 'react';
import { publicRuntimeConfig } from '../../../config/env';
const { NEXT_PUBLIC_ENVIRONTMENT } = publicRuntimeConfig;

export default function ImageWithFallback({ src, alt, className, iconFallback, ...props }: Props) {
    const [isError, setIsError] = useState<any | null>(null);

    useEffect(() => {
        setIsError(false);
    }, [src]);

    return isError ? (
        <div className={clsx('bg-dark-quaternary justify-center flex', className)}>
            {iconFallback || (
                <Image
                    src={'/image/img-broken.png'}
                    alt='img-broken'
                    width={0}
                    height={0}
                    sizes='100vw'
                    className='w-12 h-12 object-contain'
                />
            )}
        </div>
    ) : (
        <Image
            id={src}
            src={src || ''}
            alt={alt}
            className={className}
            width={0}
            height={0}
            {...props}
            sizes='100vw'
            onError={() => {
                setIsError(true);
            }}
        />
    );
}

type Props = {
    src: string;
    alt: string;
    className?: string;
    iconFallback?: any;
} & ImageProps;
