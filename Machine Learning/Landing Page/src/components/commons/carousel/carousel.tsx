"use client"
import * as React from "react"

import { Card, CardContent } from "@/components/ui/card"
import {
    Carousel,
    CarouselContent,
    CarouselItem,
    CarouselNext,
    CarouselPrevious,
} from "@/components/ui/carousel"
import clsx from "clsx"
import Autoplay from "embla-carousel-autoplay"

export default function CarouselOrientation({
    opts = { align: "start" },
    orientation = "vertical",
    classNameCarousel,
    classNameCarouselContent,
    classNameCarouselItem = "md:basis-1/2",
    classNameCarouselPrevious,
    classNameCarouselNext,
    classNameCard,
    classNameCardContent,
    data,
    delayAnimation = 2000,
    children
}: CarouselProps) {
    return (
        <Carousel
            opts={{
                align: opts.align,
            }}
            plugins={[
                Autoplay({
                    delay: delayAnimation,
                }),
            ]}
            orientation={orientation}
            className={clsx("w-full", classNameCarousel)}
        >
            <CarouselContent className={clsx("-mt-1 h-[200px]", classNameCarouselContent)}>
                {children}
            </CarouselContent>
            <CarouselPrevious className={clsx(classNameCarouselPrevious)} />
            <CarouselNext className={clsx(classNameCarouselNext)} />
        </Carousel>
    )
}

type CarouselProps = {
    opts?: {
        align?: "start" | "center" | "end"
    }
    orientation?: "horizontal" | "vertical"
    classNameCarousel?: string
    classNameCarouselContent?: string
    classNameCarouselItem?: string
    classNameCarouselPrevious?: string
    classNameCarouselNext?: string
    classNameCard?: string
    classNameCardContent?: string
    data?: any
    delayAnimation?: number
    children?: React.ReactNode
}
