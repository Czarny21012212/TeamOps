import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useEffect, useState } from "react";
import { Building2, Plus } from "lucide-react";

type ChildProps = {
    companyId: number;
}

type DepartmentData = {
    companyId: number;
    depName: string;
};

export default function DepartmentCreator({companyId}: ChildProps) {

    const [depData, setDepData] = useState<DepartmentData>({
        companyId: companyId,
        depName: ""
    });
    const [message, setMessage] = useState<string>("");
    const [isLoading, setIsLoading] = useState(false);

    function checkDepData(){
        if (depData.depName === "") {
            setMessage("Proszę wprowadzić nazwę działu.");
            return;
        }
        handleCreateDepartment();
    }

    function handleCreateDepartment(){
        setIsLoading(true);
        fetch("http://localhost:8081/api/createDepartment", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(depData),
            credentials: "include"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            console.log("Department created successfully:", data);
            setMessage(data.message || "Dział został utworzony pomyślnie!");
            setTimeout(() => {
                window.location.reload();
            }, 1500);
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
            setMessage("Błąd przy tworzeniu działu: " + error.message);
        })
        .finally(() => {
            setIsLoading(false);
        });
    }

    console.log("Department Data:", depData);

    return(
        <div className="bg-zinc-950 border border-zinc-800 rounded-lg p-6 mb-6 max-w-md">
            <Card className="bg-zinc-900 border-zinc-800">
                <CardHeader>
                    <CardTitle className="text-white flex items-center gap-2">
                        <Building2 className="w-5 h-5" />
                        Utwórz nowy dział
                    </CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                    <div className="space-y-2">
                        <label className="text-zinc-300 text-sm font-medium">
                            Nazwa działu
                        </label>
                        <input
                            placeholder="Wprowadź nazwę działu..."
                            name="depName"
                            value={depData.depName}
                            onChange={(e) =>
                                setDepData({
                                    ...depData,
                                    [e.target.name]: e.target.value,
                                })
                            }
                            className="w-full bg-zinc-800 border border-zinc-700 text-white rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                        />
                    </div>

                    {message && (
                        <div className={`p-3 rounded-lg ${
                            message.includes("pomyślnie") || message.includes("successfully") || message.includes("created")
                                ? "bg-green-900/50 border border-green-700 text-green-300"
                                : "bg-red-900/50 border border-red-700 text-red-300"
                        }`}>
                            {message}
                        </div>
                    )}

                    <Button 
                        onClick={() => checkDepData()}
                        disabled={isLoading || !depData.depName.trim()}
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2"
                    >
                        {isLoading ? (
                            <>
                                <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></div>
                                Tworzenie...
                            </>
                        ) : (
                            <>
                                <Plus className="w-4 h-4" />
                                Utwórz dział
                            </>
                        )}
                    </Button>
                </CardContent>
            </Card>
        </div>
    );
}