package com.bot.social.media.botSocialMedia.domain.usecase.instagram;

import com.bot.social.media.botSocialMedia.domain.model.PostModel;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.actions.users.UserAction;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.requests.friendships.FriendshipsActionRequest;
import com.github.instagram4j.instagram4j.responses.feed.FeedUsersResponse;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.DATE_FORMATTER;
import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.SPREADSHEETID;

@RequiredArgsConstructor
public class InstagramUseCaseImpl {

    private final Sheets sheets;
    private final Storage storage;

    @Value("${username.ig}")
    private String username;
    @Value("${password.ig}")
    private String password;

    public void publishNow(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        final String range = "instagram!A2:C2";
        try {
            ValueRange response = sheets.spreadsheets().values().get(SPREADSHEETID, range).execute();
            List<Object> values = response.getValues().get(0);
            if (values == null || values.isEmpty()){
                System.out.println("Data not found");
            }else{
                PostModel post = PostModel.builder().description(values.get(0).toString()).imageUrl(values.get(1).toString())
                        .date(LocalDate.parse(values.get(2).toString(), formatter)).build();

                if(post.getDate().isBefore(LocalDate.now()) || post.getDate().isEqual(LocalDate.now())){
                    DeleteRangeRequest deleteRangeRequest = new DeleteRangeRequest()
                            .setRange(new GridRange()
                                    .setSheetId(0)
                                    .setStartRowIndex(1)
                                    .setEndRowIndex(2)
                                    .setStartColumnIndex(0)
                                    .setEndColumnIndex(3))
                            .setShiftDimension("ROWS");
                    List<Request> requests = new ArrayList<>();
                    requests.add(new Request().setDeleteRange(deleteRangeRequest));
                    BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
                    sheets.spreadsheets().batchUpdate(SPREADSHEETID, body).execute();
                    publishOnIg( post.getImageUrl(), post.getDescription());
                    return;
                }
            }
        }catch (IOException ex){
            System.out.println("Errr:" + ex);
        }
        System.out.print("No se publicó nada");
        PostModel.builder().build();
    }

    public void publishOnIg(String imageUrl, String description) throws IGLoginException {

        IGClient client = IGClient.builder().username(username).password(password).login();
        getImageFromFirebase(imageUrl);
        client.actions().timeline().uploadPhoto(new File("src/main/resources/" + imageUrl), description)
                .thenAccept(response -> {
            System.out.println("Foto subida correctamente");
            deleteImage(imageUrl);
        }).join();
    }

    public void getImageFromFirebase(String nameFile){

        Blob blob = storage.get(BlobId.of(System.getenv("BUCKETFIREBASE"), nameFile));
        blob.downloadTo(Path.of(new File("src/main/resources/"+nameFile).getAbsolutePath()));
    }

    public void deleteImage(String nameFile){
        File image = new File("src/main/resources/"+nameFile);
        if (image.delete()){
            System.out.print("Se elimino imagen");
        }else{
            System.out.print("NO se elimino la imagen");
        }
    }

    public String unfollow(List<String> exceptionCounts) throws IGLoginException, ExecutionException, InterruptedException {
        IGClient client = IGClient.builder().username(username).password(password).login();
        List<FeedUsersResponse> followers = new UserAction(client, client.getActions().account().currentUser().get().getUser()).followersFeed().stream().toList();
        List<FeedUsersResponse> following = new UserAction(client, client.getActions().account().currentUser().get().getUser()).followingFeed().stream().toList();
        int numFollowers = followers.stream().mapToInt(aux -> aux.getUsers().size()).sum();
        int numFollwing = following.stream().mapToInt(aux -> aux.getUsers().size()).sum();
        System.out.println(exceptionCounts.toString());
        System.out.printf("Followers: %d y Following: %d\n", numFollowers, numFollwing);
        List<String> eliminarUsuarios = new ArrayList();
        List<String> followersFinal = new ArrayList();
        List<String> followingFinal = new ArrayList();

        followers.forEach(aux -> aux.getUsers().forEach(user -> followersFinal.add(user.getUsername())));
        following.forEach(aux -> aux.getUsers().forEach(user -> followingFinal.add(user.getUsername()+","+user.getPk())));

        followingFinal.forEach(username -> {
            String usr = username.split(",")[0];
            if(followersFinal.contains(usr)){
                System.out.println(usr +" -> Nos seguimos");
            }else if (exceptionCounts.contains(usr)){
                System.out.println(usr + " -> No me sigue pero me gusta el contenido");
            }else{
                System.out.println(usr + " -> No me sigue, Dejar de seguir");
                eliminarUsuarios.add(username.split(",")[1]);
            }
        });
        eliminarUsuarios.forEach(pkUsuario -> {
            FriendshipsActionRequest request = new FriendshipsActionRequest(Long.parseLong(pkUsuario), FriendshipsActionRequest.FriendshipsAction.DESTROY);
            client.sendRequest(request).thenAccept(response -> {
               if (response.getStatus().equals("ok")){
                   System.out.println("Usuario eliminado " + pkUsuario);
               }else{
                   System.out.println("Fallo el unfollow " + pkUsuario);
               }
            });
        });
        return "Usuarios eliminados: " + eliminarUsuarios.size();
    }

}
