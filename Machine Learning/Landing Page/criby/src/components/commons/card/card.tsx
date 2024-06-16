import React from 'react';
import { IconType } from 'react-icons';

interface CardProps {
  title: string;
  description: string;
  icon: IconType;
}

function Card({ title, description, icon: Icon }: CardProps) {
  return (
    <div className="flex flex-col items-center justify-center py-5 gap-5 w-30">
      <div className="flex items-center justify-center">
        <Icon className="w-auto md:h-28 h-14"></Icon>
      </div>
      <p className="text-center md:text-[25px] text-[20px] font-[500]">{title}</p>
      <p className="text-center md:text-[20px] text-[15px]">{description}</p>
    </div>
  );
}

export default Card;
