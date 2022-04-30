package com.binklac.bukkit.sample.ClassModifier;

import com.binklac.jhook.Utils;
import net.minecraft.server.players.PlayerList;
import org.objectweb.asm.*;

import java.io.IOException;

public class PlayerListModifier {
    private static class PlayerListVisitor extends ClassVisitor {
        public PlayerListVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);

            if (methodVisitor != null && name.equals("a") && descriptor.equals("(Lcom/mojang/authlib/GameProfile;)V")) {
                methodVisitor = new AddOpMethodEnterAdapter(api, methodVisitor);
            }

            return methodVisitor;
        }

        private static class AddOpMethodEnterAdapter extends MethodVisitor {
            public AddOpMethodEnterAdapter(int api, MethodVisitor methodVisitor) {
                super(api, methodVisitor);
            }

            @Override
            public void visitCode() {
                super.visitVarInsn(Opcodes.ALOAD, 1);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/binklac/bukkit/sample/EventFireHelper", "firePlayerOpEvent", "(Lcom/mojang/authlib/GameProfile;)V", false);
                super.visitCode();
            }
        }
    }

    public static byte[] GetModifiedByteCode() throws IOException {
        ClassReader playerListClassReader = new ClassReader(Utils.getClassAsByteArray(PlayerList.class.getClassLoader(), PlayerList.class.getCanonicalName()));
        ClassWriter playerListClassWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor playerListVisitor = new PlayerListVisitor(Opcodes.ASM9, playerListClassWriter);
        playerListClassReader.accept(playerListVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        return playerListClassWriter.toByteArray();
    }
}

