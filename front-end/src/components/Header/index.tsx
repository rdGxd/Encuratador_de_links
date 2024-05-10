import { cn } from "@/lib/utils";
import Link from "next/link";

export const Header = () => {
  return (
    <Link href={"/links"}>
      <header className="mt-3 flex  items-center justify-around">
        <h1
          className={cn(
            "inline-block bg-gradient-to-r from-Brand-Primary-Pink  to-Brand-Primary-Blue bg-clip-text text-3xl font-extrabold text-transparent lg:text-4xl xl:text-5xl 2xl:text-3xl",
          )}
        >
          Linkly
        </h1>
      </header>
    </Link>
  );
};
