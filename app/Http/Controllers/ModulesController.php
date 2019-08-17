<?php

namespace App\Http\Controllers;
use App\Modules;
use Illuminate\Http\Request;
use Response;

class ModulesController extends Controller
{
    //Retrieve Modules for Selected Subject
    public function index($subject_id)
    {
        $chapters = Modules::where('subject_id', $subject_id)->get()->toArray();
        $toSend = [];
        if(count($chapters)!=0)
        {
        	$toSend = array(
        		"success" => true,
        		"data" => $chapters,
        		"message" => "Chapters availbale for selected subject"
        	);
        }
        else{
        	$toSend = array(
        		"success" => false,
        		"data" => $chapters,
        		"message" => "Nothing recieved"
        	);
        }
        return Response::json($toSend);
    }
}
