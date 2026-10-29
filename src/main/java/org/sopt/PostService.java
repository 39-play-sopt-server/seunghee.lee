package org.sopt;
import org.springframework.data.domain.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service @Transactional public class PostService {
 private final PostRepository postRepository; private final MemberService memberService;
 public PostService(PostRepository p,MemberService m){postRepository=p;memberService=m;}
 public Post create(PostRequest r){return postRepository.save(new Post(r.title(),r.content(),memberService.find(r.memberId()),r.category()));}
 @Transactional(readOnly=true) public Page<Post> findAll(Category c,Pageable p){return c==null?postRepository.findAll(p):postRepository.findByCategory(c,p);}
 public Post findDetail(Long id){Post p=get(id);p.increaseViewCount();return p;} public Post update(Long id,PostRequest r){Post p=get(id);p.update(r.title(),r.content(),r.category());return p;} public void delete(Long id){postRepository.delete(get(id));} public Post updateCategory(Long id,Category c){Post p=get(id);p.update(p.getTitle(),p.getContent(),c);return p;} private Post get(Long id){return postRepository.findById(id).orElseThrow(PostNotFoundException::new);}
}
