<?php

namespace App\Http\Controllers;
use App\Files;
use Illuminate\Http\Request;
use Response;

class FilesController extends Controller
{
     //Retrieve Files URL for Selected Chapter or Module
     public function index($module_id, $lang)
     {
         $files = Files::where('module_id', $module_id)->where('medium', $lang)->get()->toArray();
         $toSend = [];
         if(count($files)!=0)
         {
        	$toSend = array(
        		"success" => true,
        		"data" => $files,
        		"message" => "Files available for selected module and medium"
        	);
         }
         else{
        	$toSend = array(
        		"success" => false,
        		"data" => $files,
        		"message" => "Nothing recieved"
        	);
         }
        return Response::json($toSend);
     }
}
