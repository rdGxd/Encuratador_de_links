import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { Noto_Sans_Display } from "next/font/google";

const Noto = Noto_Sans_Display({
  subsets: ["latin"],
  weight: ["400", "700"],
});

export default function App({ Component }: AppProps) {
  return (
    <main className={Noto.className}>
      <Component />
    </main>
  );
}
