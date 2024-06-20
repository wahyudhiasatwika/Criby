// 'use client'
// import { FaWhatsapp } from "react-icons/fa";
// import {
//     Tooltip,
//     TooltipContent,
//     TooltipProvider,
//     TooltipTrigger,
// } from "@/components/ui/tooltip"
// import Link from "next/link";
// export default function FloatingWhatsapp({ className }: Props) {
//     return (
//         <>
//             {/* <div className="fixed z-20 bottom-16 right-10">
//                 <div className="max-w-[400px] w-full h-[200px] rounde bg-red-500 mb-5 rounded-l-3xl rounded-tr-3xl p-5">
//                     <div>
//                         <p>
//                             Selamat datang di Peluang!
//                         </p>
//                         <p>
//                             Senang bertemu dengan Anda! Jika Anda memiliki pertanyaan tentang layanan kami, jangan ragu untuk menghubungi kami.
//                         </p>
//                     </div>
//                 </div>
//             </div>
//             <div className='fixed z-20 bottom-5 right-5 transform active:scale-y-[.120] cursor-pointer outline-none transition-transform bg-custom-pink p-2 rounded-full'>
//                 <FaWhatsapp className='text-white w-8 h-8' />
//             </div> */}
//             <div className="fixed z-20 bottom-5 right-5">
//                 <TooltipProvider>
//                     <Tooltip>
//                         <TooltipTrigger asChild>
//                             <a target="_blank" href="https://wa.me/6281318406700">
//                                 <div className='transform active:scale-y-[.120] cursor-pointer outline-none transition-transform bg-custom-pink p-2 rounded-full'>
//                                     <FaWhatsapp className='text-[#25D366] sm:w-12 sm:h-12 w-8 h-8' />
//                                 </div>
//                             </a>
//                         </TooltipTrigger>
//                         <TooltipContent className=" max-w-[320px] mr-10 mb-4 rounded-l-3xl rounded-tr-3xl p-5 shadow-lg border border-custom-pink/50">
//                             <div className="flex flex-col gap-2 justify-start items-start">
//                                 <p className="font-bold text-[20px]">
//                                     Selamat datang di ASCO Dewi Sartika!
//                                 </p>
//                                 <p className="leading-relaxed">
//                                     Senang bertemu dengan Anda! Jika Anda memiliki pertanyaan tentang layanan kami, jangan ragu untuk menghubungi kami.
//                                 </p>
//                             </div>
//                         </TooltipContent>
//                     </Tooltip>
//                 </TooltipProvider>
//             </div >
//         </>
//     )
// }

// type Props = {
//     className?: string;
// }