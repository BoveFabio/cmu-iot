<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" src="ajax1.js"></script>
        <script type="text/javascript" src="cart.js"></script>
        <title>This is a photon tracker</title>
    </head>
    <body>
        <div style="float: left; width: 500px">
            <h2>Photon status</h2>
            <table border="1">
                <tbody>
                    <tr><td>ID:</td><td id="photonId"></td></tr>
                    <tr><td>Last heartbeat:</td><td id="lastHeartbeat"></td></tr>
                    <tr><td><button onclick="requestUpdate()">Refresh</button></td><td></td></tr>
                </tbody>
            </table>
        </div>
    </body>
</html>
