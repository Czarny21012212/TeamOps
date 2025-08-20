'use client';

import { Button } from "@/components/ui/button";
import Navbar from "../components/navbar/page";
import { useState } from "react";

export default function Tasks(){

    const [displayReceivedTasks, setDisplayReceivedTasks] = useState<Boolean>(true);
    const [displaySentTasks, setDisplaySentTasks] = useState<Boolean>(false);

    return(
        <div className="flex min-h-screen bg-black text-white">
        {/* Sidebar (Navbar) */}
        <div className="w-[150px] bg-black p-4">
            <Navbar activeRoute="/tasks" />
        </div>
        {/* Main Content */}
        <div className="flex-2 p-4">
            <div className="w-full max-w-2xl mx-auto">
            <div className="flex items-center gap-5">
                <h1 className="text-4xl font-semibold mb-3">Zadania</h1>
                <div className="flex gap-5 ">
                    <Button
                    onClick={() => {
                        setDisplayReceivedTasks(true);
                        setDisplaySentTasks(false);
                    }}
                    >
                    Odebrane
                    </Button>
                    <Button
                    onClick={() => {
                        setDisplayReceivedTasks(false);
                        setDisplaySentTasks(true);
                    }}
                    >
                    Wysłane
                    </Button>
                </div>
            </div>
            {/* Tasks Div (Centered Below Main Content) */}
            <div className="mt-6 w-full">
                {displayReceivedTasks && (
                <div className="text-center bg-gray-900 p-4 rounded-lg">
                    Lista zadań odebranych
                </div>
                )}
                {displaySentTasks && (
                <div className="text-center bg-gray-900 p-4 rounded-lg">
                    Lista zadań wysłanych
                </div>
                )}
            </div>
            </div>
        </div>
        </div>
    )
}