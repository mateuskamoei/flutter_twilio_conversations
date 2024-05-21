@JS()

import 'package:flutter_twilio_conversations_web/interop/classes/client.dart';
import 'package:flutter_twilio_conversations_web/methods/mapper.dart';
import 'package:js/js.dart';


Future<dynamic?> createTwilioConversationsClient(
    String token, dynamic properties) async {
  try {
    var client = await TwilioConversationsClient(token);
    //var channels = await promiseToFuture<dynamic>(client.createConversation());
    // print(client.reachabilityEnabled());
    //  await promiseToFuture<dynamic>(createConversation(token, properties));

    return Mapper.chatClientToMap(client);
  } catch (e) {
    print(e);
  }
}


getTwilioConversationBySidOrUniqueName(String channelSidOrUniqueName){

}
